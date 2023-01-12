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
public class EmployeeBranchServiceDto {

    @NotNull
    @NotEmpty
    private Long branchId;
    
    @NotNull
    @NotEmpty
    private Long employeeId;

    private Long serviceId;

    @NotNull
    @NotEmpty
    private boolean isActive;


}
