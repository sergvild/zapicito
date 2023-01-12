package com.ruso.zapicito.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class BranchEmployeeDto {

    private Long employeeId;

    @NotNull
    @NotEmpty
    @JsonProperty("isActive")
    private boolean isActive;


}
