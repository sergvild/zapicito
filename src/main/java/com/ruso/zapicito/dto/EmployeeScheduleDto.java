package com.ruso.zapicito.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeScheduleDto {
    private Long employeeId;
    private String name;
    private Long scheduleId;
}