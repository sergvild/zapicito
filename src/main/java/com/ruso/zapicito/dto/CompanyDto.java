package com.ruso.zapicito.dto;

import com.ruso.zapicito.validator.PasswordMatches;
import com.ruso.zapicito.validator.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {
    @NotNull
    @NotEmpty
    private String name;
    
    @NotNull
    @NotEmpty
    private String description;

    @NotNull
    @NotEmpty
    private String address;

    @NotNull
    @NotEmpty
    private String phone;
}
