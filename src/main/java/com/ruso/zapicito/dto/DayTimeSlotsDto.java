package com.ruso.zapicito.dto;

import lombok.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DayTimeSlotsDto {
    private String day;
    private Set<ScheduleDto.TimeSlot> days = new HashSet<>();
}
