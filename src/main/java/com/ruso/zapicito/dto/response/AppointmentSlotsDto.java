package com.ruso.zapicito.dto.response;

import com.ruso.zapicito.dto.ScheduleDto;
import com.ruso.zapicito.dto.SpotDto;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class AppointmentSlotsDto {

    private String today;
    private String format;
    private String availabilityTimezone;
    private List<DayDto> days;

    @Data
    public static class DayDto {
        private String date;
        private List<SpotDto> spots;
        private Set<ScheduleDto.TimeSlot> intervals;
    }
}
