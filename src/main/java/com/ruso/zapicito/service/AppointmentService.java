package com.ruso.zapicito.service;

import com.ruso.zapicito.dto.BookingDto;
import com.ruso.zapicito.dto.CustomerDto;
import com.ruso.zapicito.dto.AppointmentDto;
import com.ruso.zapicito.dto.ScheduleDto;
import com.ruso.zapicito.dto.response.AppointmentSlotsDto;
import com.ruso.zapicito.entity.Appointment;
import com.ruso.zapicito.entity.Branch;
import com.ruso.zapicito.entity.Customer;
import com.ruso.zapicito.exception.ZapicitoException;
import com.ruso.zapicito.mapper.AppointmentMapper;
import com.ruso.zapicito.repository.AppointmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AppointmentService {

    private static final int APPOINTMENT_INTERVAL_MINUTES = 15;

    private final CompanyService companyService;
    private final CustomerService customerService;
    private final ServicesService servicesService;
    private final BranchService branchService;
    private final ScheduleService scheduleService;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;


    @Transactional
    public void createAppointment(BookingDto bookingDto, Long companyId) throws ZapicitoException {
        CustomerDto customerDto = CustomerDto.fromBookingDto(bookingDto);

        Customer customer = customerService
                .findCustomerByPhone(bookingDto.getPhone())
                .orElseGet(()-> {
                    try {
                        return customerService.saveCustomer(customerDto, companyId);
                    } catch (ZapicitoException e) {
                        throw new RuntimeException(e);
                    }
                });

        com.ruso.zapicito.entity.Service service = servicesService.findServiceById(bookingDto.getProductId());
        Branch branch = branchService.findBranchById(bookingDto.getBranchId());

        Appointment appointment = new Appointment();
        appointment.setCustomer(customer);
        appointment.setService(service);
        appointment.setBranch(branch);
        appointment.setAppointmentAt(bookingDto.getReservedOn());
        appointmentRepository.save(appointment);
    }

    public List<AppointmentDto> findAppointmentsByCustomer(Long customerId) {
        return appointmentRepository.findAllByCustomerId(customerId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }


    public AppointmentDto mapToDto(Appointment appointment) {
        AppointmentDto appointmentResponseDTO = appointmentMapper.toDto(appointment);
        AppointmentDto.TimeDTO timeDTO = new AppointmentDto.TimeDTO();

        com.ruso.zapicito.entity.Service service = appointment.getService();

        Integer serviceDuration = service.getFullDuration();

        timeDTO.setStart(appointment.getAppointmentAt()
                .format(DateTimeFormatter.ofPattern("HH:mm")));
        timeDTO.setEnd(appointment.getAppointmentAt()
                .plusMinutes(serviceDuration)
                .format(DateTimeFormatter.ofPattern("HH:mm")));

        appointmentResponseDTO.setTime(timeDTO);


        return appointmentResponseDTO;
    }

    public AppointmentSlotsDto findAppointmentSlots(String companyId,
                                                    LocalDate rangeStart,
                                                    LocalDate rangeEnd,
                                                    Long serviceId,
                                                    Long branchId,
                                                    Long userId) throws ZapicitoException {

        com.ruso.zapicito.entity.Service service = servicesService.findServiceById(serviceId);

        List<ScheduleDto> scheduleList = scheduleService.getSchedulesByStartAndEndDate(branchId, rangeStart, rangeEnd);

        scheduleList.forEach(scheduleDto -> {
            Map<String, List<ScheduleDto.TimeSlot>> days = scheduleDto.getDays();
            days.forEach((day, timeSlots) -> {
                generateSpots(day, timeSlots, 1, service.getPrice());
            });
        });


        if (userId == null){
            return null;
        }

        //result.addAll(generateSpots((String) interval.get("start"), (String) interval.get("end"), stepMinutes, durationMinutes, date, quantity, price, currentTime));

        return null;
    }

    public List<Map<String, Object>> generateSpots(String day, List<ScheduleDto.TimeSlot> timeSlots, int quantity, Integer price){
        return
                timeSlots.stream()
                        .map(timeSlot -> generateSpot(timeSlot.getStart(), timeSlot.getEnd(),
                                APPOINTMENT_INTERVAL_MINUTES, 10,
                                quantity, price ))
                .flatMap(Collection::stream)
                        .toList();
    }


    public List<Map<String, Object>> generateSpot(String startTime, String endTime,
                                                          int stepMinutes, int durationMinutes,
                                                          int quantity,
                                                          int price) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime start = LocalTime.parse(startTime, formatter);
        LocalTime end = LocalTime.parse(endTime, formatter);
        LocalTime now = LocalTime.now();

        List<Map<String, Object>> spots = new ArrayList<>();
        LocalTime current = start;

        while (current.plusMinutes(durationMinutes).isBefore(end) || current.plusMinutes(durationMinutes).equals(end)) {
            if (!current.isBefore(now) && (current.toSecondOfDay() - start.toSecondOfDay()) % (stepMinutes * 60) == 0) {
                Map<String, Object> spot = new HashMap<>();
                spot.put("start", current.format(formatter));
                spot.put("end", current.plusMinutes(durationMinutes).format(formatter));
                spot.put("quantity", quantity);
                spot.put("prices", price);

                spots.add(spot);
            }
            current = current.plusMinutes(stepMinutes);
        }

        return spots;
    }
}
