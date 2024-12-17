package com.ruso.zapicito.controller;

import com.ruso.zapicito.dto.SlotDto;
import com.ruso.zapicito.service.BookingService;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/{companyId}/slots/{employeeId}")
    public ResponseEntity<SlotDto> findAvailableSlots(@PathVariable @ApiParam(name = "companyId", value = "Company id", example = "123423") Long companyId,
                                                            @PathVariable @ApiParam(name = "employeeId", value = "Employee Id", example = "123134") Long employeeId){
        SlotDto slot = bookingService.findAvailableSlots();
        return new ResponseEntity<>(slot, HttpStatus.ACCEPTED);
    }

}
