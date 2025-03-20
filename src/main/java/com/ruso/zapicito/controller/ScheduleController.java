package com.ruso.zapicito.controller;

import com.ruso.zapicito.dto.ApiResponse;
import com.ruso.zapicito.dto.EmployeeScheduleDto;
import com.ruso.zapicito.dto.PeriodDto;
import com.ruso.zapicito.dto.ScheduleDto;
import com.ruso.zapicito.entity.Period;
import com.ruso.zapicito.exception.ZapicitoException;
import com.ruso.zapicito.service.ScheduleService;
import com.ruso.zapicito.util.ResponseUtil;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }


    @PostMapping("/{id}/periods")
    public ResponseEntity<ApiResponse<String>> createPeriods(@PathVariable Long id,
                                                     @RequestBody List<PeriodDto> periodsDto) throws ZapicitoException {


        List<Period> periodList = scheduleService.mapToPeriodList(periodsDto);
        scheduleService.createSchedule(id, periodList);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ScheduleDto>> findPeriods(@PathVariable Long id,
                                                   @RequestParam("range_start") String rangeStart,
                                                   @RequestParam("range_end") String rangeEnd) throws ZapicitoException {

        ScheduleDto schedule = scheduleService.getScheduleByStartAndEndDate(id, rangeStart, rangeEnd);

        return new ResponseEntity<>(ResponseUtil.success(schedule), HttpStatus.ACCEPTED);
    }

    @GetMapping("/branches/{branchId}")
    public ResponseEntity<ApiResponse<List<EmployeeScheduleDto>>> findEmployeeSchedule(@PathVariable Long branchId) {

        return new ResponseEntity<>(ResponseUtil.success(scheduleService.getEmployeeSchedules(branchId)),
                HttpStatus.ACCEPTED);
    }

    @GetMapping("/branches/{branchId}/periods")
    public ResponseEntity<ApiResponse<List<ScheduleDto>>> findBranchSchedules(@PathVariable Long branchId,
                                                                              @RequestParam("range_start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate rangeStart,
                                                                              @RequestParam("range_end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate rangeEnd) {

        List<ScheduleDto> response = scheduleService.getSchedulesByStartAndEndDate(branchId, rangeStart, rangeEnd);

        return new ResponseEntity<>(ResponseUtil.success(response), HttpStatus.ACCEPTED);
    }

}
