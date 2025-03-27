package com.ruso.zapicito.controller;

import com.ruso.zapicito.dto.*;
import com.ruso.zapicito.dto.response.AppointmentSlotsDto;
import com.ruso.zapicito.entity.Category;
import com.ruso.zapicito.entity.Service;
import com.ruso.zapicito.exception.ZapicitoException;
import com.ruso.zapicito.service.AppointmentService;
import com.ruso.zapicito.service.ServicesService;
import com.ruso.zapicito.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping("company/{companyId}")
    public ResponseEntity<String> createAppointment(@PathVariable Long companyId, @RequestBody BookingDto bookingDto) throws ZapicitoException {
        appointmentService.createAppointment(bookingDto, companyId);
        return new ResponseEntity<>("Created", HttpStatus.ACCEPTED);
    }

    @GetMapping("/company/{companyId}/calendars")
    public ResponseEntity<ApiResponse<AppointmentSlotsDto>> findAppointmentSlots(
            @PathVariable String companyId,
            @RequestParam("range_start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate rangeStart,
            @RequestParam("range_end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate rangeEnd,
            @RequestParam(value = "service") Long serviceId,
            @RequestParam(value = "branch") Long branchId,
            @RequestParam(value = "user", required = false) Long userId) throws ZapicitoException {

        AppointmentSlotsDto appointmentSlots = appointmentService
                .findAppointmentSlots(rangeStart, rangeEnd, serviceId, branchId, userId);

        return ResponseEntity.ok(ResponseUtil.success(appointmentSlots));
    }

    @GetMapping("/company/{companyId}/services")
    public ResponseEntity<ApiResponse<List<CategoryServiceDto>>> getAllServicesByBranch(@PathVariable Long companyId,
                                                                                        @RequestParam Long branchId) throws ZapicitoException {
        return new ResponseEntity<>(ResponseUtil.success(appointmentService.getAllServicesByBranch(branchId)), HttpStatus.ACCEPTED);
    }

    //https://booking.easyweek.io/api/v1/bookings/company/test-614/employees/dates?service=372544&location=122366
    @GetMapping("/company/{companyId}/employees/dates")
    public ResponseEntity<ApiResponse<ScheduleDatesDto>> getAllServicesByBranch(@PathVariable Long companyId,
                                                                               @RequestParam Long service,
                                                                               @RequestParam Long branch) throws ZapicitoException {
        return new ResponseEntity<>(ResponseUtil.success(appointmentService.getScheduleDatesByBranch(branch,service)), HttpStatus.ACCEPTED);
    }

}
