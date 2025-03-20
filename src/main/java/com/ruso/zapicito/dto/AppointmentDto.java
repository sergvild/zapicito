package com.ruso.zapicito.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AppointmentDto {
    private Long id;
    private String uuid;
    private String orderHashId;
    private Long userId;
    private Long createdById;
    private Long closedById;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime closedAt;

    private Long leadStatusId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime appointmentAt;

    private LocalDate date;
    private TimeDTO time;
    private List<Long> relatedIds;
    private Integer source;
    private String status;
    private Integer subtotal;
    private Integer amountPaid;
    private Integer total;
    private Long closureReasonId;
    private String closureReasonDescription;
    private boolean startingAt;
    private String image;
    private String productName;
    private Integer productCount;
    private CustomerDto customer;
    private ServiceDto service;
    private BranchDto branch;

    @Data
    public static class TimeDTO {
        private String start;
        private String end;
    }

 /*   @Data
    private class CustomerDTO {
        private Long id;
        private String deletedAt;
        private String phone;
        private String firstName;
        private String lastName;
        private String middleName;
        private String birthday;
        private String comment;
        private String email;
        private String browserLanguage;
    }*/

}
