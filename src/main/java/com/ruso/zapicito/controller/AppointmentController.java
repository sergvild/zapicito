package com.ruso.zapicito.controller;

import com.ruso.zapicito.dto.ApiResponse;
import com.ruso.zapicito.dto.BookingDto;
import com.ruso.zapicito.dto.AppointmentDto;
import com.ruso.zapicito.dto.response.AppointmentSlotsDto;
import com.ruso.zapicito.exception.ZapicitoException;
import com.ruso.zapicito.service.AppointmentService;
import com.ruso.zapicito.util.ResponseUtil;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("company/{companyId}")
    public ResponseEntity<String> createAppointment(@PathVariable Long companyId, @RequestBody BookingDto bookingDto) throws ZapicitoException {
        appointmentService.createAppointment(bookingDto, companyId);
        return new ResponseEntity<>("Created", HttpStatus.ACCEPTED);
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<ApiResponse<List<AppointmentDto>>> findAppointments(@PathVariable Long customerId) {
        List<AppointmentDto> appointments = appointmentService.findAppointmentsByCustomer(customerId);
        return new ResponseEntity<>(ResponseUtil.success(appointments), HttpStatus.ACCEPTED);
    }

    @GetMapping("/company/{companyId}/calendars")
    public ResponseEntity<ApiResponse<AppointmentSlotsDto>> findAppointmentSlots(
            @PathVariable String companyId,
            @RequestParam("range_start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate rangeStart,
            @RequestParam("range_end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate rangeEnd,
            @RequestParam(value = "service") Long serviceId,
            @RequestParam(value = "branch") Long branchId,
            @RequestParam(value = "user", required = false) Long userId) throws ZapicitoException {

        AppointmentSlotsDto appointmentSlots = appointmentService.findAppointmentSlots(
                companyId, rangeStart, rangeEnd, serviceId, branchId, userId);

        return ResponseEntity.ok(ResponseUtil.success(appointmentSlots));
    }

}
