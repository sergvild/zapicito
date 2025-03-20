package com.ruso.zapicito.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingDto {

    private Long userId;
    private LocalDateTime reservedOn;
    private int quantity;
    private Long branchId;
    private Long productId;
    private List<Long> relatedIds;
    private String phone;
    private String firstName;
    private String lastName;
    private String bookingComment;
    private String browserLanguage;
    private String browserTz;
    private String paymentProvider;
    private Long voucherId;
}