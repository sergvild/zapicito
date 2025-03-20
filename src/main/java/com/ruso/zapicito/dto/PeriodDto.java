package com.ruso.zapicito.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PeriodDto {
    private String date;
    private String start;
    private String end;
}