package com.ruso.zapicito.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static boolean isDateBetween(String targetDateStr, String startDateStr, String endDateStr) {
        try {
            LocalDate targetDate = LocalDate.parse(targetDateStr, formatter);
            LocalDate startDate = LocalDate.parse(startDateStr, formatter);
            LocalDate endDate = LocalDate.parse(endDateStr, formatter);

            if ((targetDate.isEqual(startDate) || targetDate.isAfter(startDate)) &&
                    (targetDate.isEqual(endDate) || targetDate.isBefore(endDate))) {

                return true;
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format: " + e.getMessage());
        }
        return false;
    }
}
