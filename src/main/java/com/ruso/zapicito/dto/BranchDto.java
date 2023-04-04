package com.ruso.zapicito.dto;

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
public class BranchDto {

    @NotNull
    @NotEmpty
    private Long companyId;

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
