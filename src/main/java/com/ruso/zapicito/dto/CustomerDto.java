package com.ruso.zapicito.dto;

import com.ruso.zapicito.validator.PasswordMatches;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatches
@Builder
public class CustomerDto {

    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String phone;
    private String sex;
    private String birthday;
    private String comment;

    public static CustomerDto fromBookingDto(BookingDto bookingDto) {
        return CustomerDto.builder()
                .firstName(bookingDto.getFirstName())
                .lastName(bookingDto.getLastName())
                .phone(bookingDto.getPhone())
                .build();
    }
}
