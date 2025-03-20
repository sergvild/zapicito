package com.ruso.zapicito.service;

import com.ruso.zapicito.dto.EmployeeScheduleDto;
import com.ruso.zapicito.dto.PeriodDto;
import com.ruso.zapicito.dto.ScheduleDto;
import com.ruso.zapicito.entity.Period;
import com.ruso.zapicito.entity.Schedule;
import com.ruso.zapicito.exception.ZapicitoException;
import com.ruso.zapicito.mapper.PeriodMapper;
import com.ruso.zapicito.repository.ScheduleRepository;
import com.ruso.zapicito.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final PeriodMapper periodMapper;

    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public void createSchedule(Long scheduleId, List<Period> periods) throws ZapicitoException {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ZapicitoException("Couldn't find schedule by ID=" + scheduleId));

        schedule.setPeriods(periods);

        scheduleRepository.save(schedule);
    }

    public Schedule findScheduleById(Long id) throws ZapicitoException {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new ZapicitoException("Couldn't find schedule by ID=" + id));
    }

    public List<Schedule> findSchedulesByBranchId(Long branchId) {
        return scheduleRepository.findByBranchId(branchId);
    }

    public Optional<Schedule> findScheduleByEmployeeIdAndBranchId(Long employeeId, Long branchId) {
        return scheduleRepository.findByEmployeeIdAndBranchId(employeeId, branchId);
    }

    public List<ScheduleDto> getSchedulesByStartAndEndDate(Long branchId, LocalDate rangeStart, LocalDate rangeEnd) {

        List<Schedule> scheduleList = findSchedulesByBranchId(branchId);

        return scheduleList.stream().map(schedule -> {
            Map<String, List<ScheduleDto.TimeSlot>> days = getDayTimeSlots(schedule, rangeStart.toString(), rangeEnd.toString());
            return new ScheduleDto(schedule.getId(), schedule.getEmployeeId(), days);

        }).toList();
    }

    private Map<String, List<ScheduleDto.TimeSlot>> getDayTimeSlots(Schedule schedule, String rangeStart, String rangeEnd) {
        return schedule.getPeriods().stream()
                .filter(period -> DateUtil.isDateBetween(period.getDate(), rangeStart, rangeEnd))
                .collect(Collectors.groupingBy(
                        Period::getDate,
                        Collectors.mapping(period -> new ScheduleDto.TimeSlot(period.getStart(), period.getEnd()),
                                Collectors.toList())
                ));
    }

    public ScheduleDto getScheduleByStartAndEndDate(Long scheduleId, String rangeStart, String rangeEnd) throws ZapicitoException {
        Schedule schedule = findScheduleById(scheduleId);

        Map<String, List<ScheduleDto.TimeSlot>> days = getDayTimeSlots(schedule, rangeStart, rangeEnd);

        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleDto.setId(schedule.getId());
        scheduleDto.setDays(days);

        return scheduleDto;
    }

    public Period mapToPeriod(PeriodDto periodDto) {
        return periodMapper.fromDto(periodDto);
    }

    public List<Period> mapToPeriodList(List<PeriodDto> periodDtoList) {
        return periodMapper.fromListDto(periodDtoList);
    }

    public  List<EmployeeScheduleDto> getEmployeeSchedules(Long branchId) {
        return scheduleRepository.findEmployeeScheduleByBranchId(branchId);

    }
}
