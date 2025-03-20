package com.ruso.zapicito.dto;

import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ScheduleDto {
    private Long id;
    private Long userId;
    private Map<String, List<TimeSlot>> days = new HashMap<>();

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class TimeSlot {
        private String start;
        private String end;
    }
}
