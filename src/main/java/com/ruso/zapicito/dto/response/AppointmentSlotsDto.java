package com.ruso.zapicito.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AppointmentSlotsDto {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate today;
    private String format;
    private String availabilityTimezone;
    private List<Day> days;

    @Data
    private class Day {
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate date;
        private List<Spot> spots;
        private List<Interval> intervals;

        @Data
        private class Spot {
            private String start;
            private String end;
            private int quantity;
            private int prices;
        }

        @Data
        private class Interval {
            private String start;
            private String end;
        }
    }
}
