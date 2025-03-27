package com.ruso.zapicito.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ScheduleDatesDto {
    private List<DateEntry> dates;

    @Data
    public static class DateEntry {
        private String date;
        private ScheduleDto.TimeSlot time;
        private BigDecimal price;
        private Long employee;
    }
}
