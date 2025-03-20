package com.ruso.zapicito.dto;

import com.ruso.zapicito.validator.PasswordMatches;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatches
public class EmployeeDto {
    private String firstName;
    private String lastName;
    private String middleName;
    private String sex;
    private String phone;
    private String position;
    private String description;

    private Long location;
    private String email;
    private String lastActivity;
    private String statusSys;
    private String statusText;
    private String type;
    private String department;
    private String password;
    private String passwordConfirmation;
    private String matchingPassword;

    private Long role;
    private Double salary;
    private List<Long> specIds;
    private Boolean notifyOnAllBookings;
    private List<Long> notifyOnAllBookingsBranches;
    private Boolean notifyOnAllBookingsSms;
    private Boolean notifyOnAllBookingsEmail;
    private Boolean notifyOnAllBookingsPush;

}
