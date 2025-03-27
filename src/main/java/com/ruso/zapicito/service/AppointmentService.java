package com.ruso.zapicito.service;

import com.ruso.zapicito.dto.*;
import com.ruso.zapicito.dto.response.AppointmentSlotsDto;
import com.ruso.zapicito.entity.Appointment;
import com.ruso.zapicito.entity.Branch;
import com.ruso.zapicito.entity.Service;
import com.ruso.zapicito.entity.Customer;
import com.ruso.zapicito.exception.ZapicitoException;
import com.ruso.zapicito.mapper.AppointmentMapper;
import com.ruso.zapicito.mapper.CategoryMapper;
import com.ruso.zapicito.mapper.ServiceMapper;
import com.ruso.zapicito.repository.AppointmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@org.springframework.stereotype.Service
public class AppointmentService {

    private static final int APPOINTMENT_INTERVAL_MINUTES = 15;

    private final CompanyService companyService;
    private final CustomerService customerService;
    private final ServicesService servicesService;
    private final BranchService branchService;
    private final ScheduleService scheduleService;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final CategoryMapper categoryMapper;
    private final ServiceMapper serviceMapper;


    @Transactional
    public void createAppointment(BookingDto bookingDto, Long companyId) throws ZapicitoException {
        CustomerDto customerDto = CustomerDto.fromBookingDto(bookingDto);

        Customer customer = customerService
                .findCustomerByPhone(bookingDto.getPhone())
                .orElseGet(() -> {
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

    public AppointmentSlotsDto findAppointmentSlots(LocalDate rangeStart,
                                                    LocalDate rangeEnd,
                                                    Long serviceId,
                                                    Long branchId,
                                                    Long userId) throws ZapicitoException {
        List<AppointmentSlotsDto.DayDto> dayDtos = new ArrayList<>();

        rangeStart = (rangeStart.isBefore(LocalDate.now())) ? LocalDate.now() : rangeStart;

        if (rangeEnd.isBefore(rangeStart))
            throw new ZapicitoException("The given data was invalid. Please fix them and try again.");

        com.ruso.zapicito.entity.Service service = servicesService.findServiceById(serviceId);

        List<ScheduleDto> scheduleList = scheduleService.getSchedulesByStartAndEndDate(branchId, rangeStart, rangeEnd);

        if (userId != null) {
            scheduleList = scheduleList.stream().filter(scheduleDto -> scheduleDto.getUserId().equals(userId)).toList();
        }

        List<Map<String, List<ScheduleDto.TimeSlot>>> listOfMaps = scheduleList.stream().map(ScheduleDto::getDays).toList();
        Map<String, Set<ScheduleDto.TimeSlot>> mergedMap = listOfMaps.stream()
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new HashSet<>(entry.getValue()), // Use HashSet to remove duplicates
                        (existing, newSet) -> {
                            existing.addAll(newSet); // Merge sets
                            return existing;
                        }
                ));

        mergedMap.forEach((day, timeSlots) -> {
            List<SpotDto> spots = new ArrayList<>(generateSpots(timeSlots, service.getPrice(), service.getDuration(), day));
            spots.sort(Comparator.comparing(SpotDto::getStart));

            AppointmentSlotsDto.DayDto appointmentDay = new AppointmentSlotsDto.DayDto();
            appointmentDay.setDate(day);
            appointmentDay.setSpots(spots);
            appointmentDay.setIntervals(timeSlots);

            dayDtos.add(appointmentDay);

        });

        AppointmentSlotsDto appointmentSlotsDto = new AppointmentSlotsDto();
        appointmentSlotsDto.setToday(LocalDate.now().toString());
        appointmentSlotsDto.setFormat("24h");
        appointmentSlotsDto.setAvailabilityTimezone("America Sao_Paulo");
        appointmentSlotsDto.setDays(dayDtos);

        return appointmentSlotsDto;
    }

    public Set<SpotDto> generateSpots(Set<ScheduleDto.TimeSlot> timeSlots, BigDecimal price, Integer duration, String currentDate) {
        return
                timeSlots.stream()
                        .map(timeSlot -> generateSpot(currentDate, timeSlot.getStart(), timeSlot.getEnd(),
                                APPOINTMENT_INTERVAL_MINUTES, duration, price))
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet());
    }


    public List<SpotDto> generateSpot(String currentDate, String startTime, String endTime,
                                      int stepMinutes, int durationMinutes,
                                      BigDecimal price) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime start = LocalTime.parse(startTime, formatter);
        LocalTime end = LocalTime.parse(endTime, formatter);
        LocalTime now = LocalTime.now();

        List<SpotDto> spots = new ArrayList<>();
        LocalTime current = start;
        boolean isToday = LocalDate.now().toString().equals(currentDate);

        while (current.plusMinutes(durationMinutes).isBefore(end) || current.plusMinutes(durationMinutes).equals(end)) {
            if (!isToday || (!current.isBefore(now))) {
                SpotDto spot = new SpotDto();
                spot.setDate(currentDate);
                spot.setStart(current.format(formatter));
                spot.setEnd(current.plusMinutes(durationMinutes).format(formatter));
                spot.setQuantity(1);
                spot.setPrice(price);
                spots.add(spot);
            }
            current = current.plusMinutes(stepMinutes);
        }

        return spots;
    }

    public List<CategoryServiceDto> getAllServicesByBranch(Long branchId) throws ZapicitoException {

        return servicesService.getAllServicesByBranch(branchId).stream()
                .collect(Collectors.groupingBy(Service::getCategory, Collectors.toList()))
                .entrySet()
                .stream()
                .map(entry -> {
                    CategoryDto categoryDto = categoryMapper.toDto(entry.getKey());
                    List<ServiceDto> serviceDtos = serviceMapper.toDtoList(entry.getValue());
                    return new CategoryServiceDto(categoryDto, serviceDtos);
                })
                .sorted(Comparator.comparing(categoryServiceDto -> categoryServiceDto.getCategory().getName()))
                .toList();
    }

    public ScheduleDatesDto getScheduleDatesByBranch(Long branchId, Long serviceId) throws ZapicitoException {
        Service service = servicesService.findServiceById(serviceId);
        ScheduleDatesDto scheduleDatesDto = new ScheduleDatesDto();

        List<ScheduleDatesDto.DateEntry> dateEntryList = new ArrayList<>();
        LocalDate now = LocalDate.now();

        List<ScheduleDto> response = scheduleService
                .getSchedulesByStartAndEndDate(branchId, now, now.plusDays(7));

        response.stream().forEach(scheduleDto -> {
            Optional<SpotDto> spotDtoOptional = scheduleDto.getDays().entrySet().stream()
                    .map(entry -> {
                        String date = entry.getKey();
                        Set<ScheduleDto.TimeSlot> timeSlots = new HashSet<>(entry.getValue());

                        return generateSpots(
                                timeSlots,
                                service.getPrice(),
                                service.getDuration(),
                                date);

                    })
                    .flatMap(Collection::stream)
                    .findFirst();

            if (spotDtoOptional.isEmpty()) {
                return;
            }

            SpotDto spotDto = spotDtoOptional.get();

            ScheduleDatesDto.DateEntry dateEntry = new ScheduleDatesDto.DateEntry();
            dateEntry.setEmployee(scheduleDto.getUserId());
            dateEntry.setDate(spotDto.getDate());
            dateEntry.setPrice(spotDto.getPrice());
            dateEntry.setTime(new ScheduleDto.TimeSlot(spotDto.getStart(), spotDto.getEnd()));

            dateEntryList.add(dateEntry);
        });

        scheduleDatesDto.setDates(dateEntryList);

        return scheduleDatesDto;

    }
}
