package com.mmf.resumeBuilder.service.datetools;

import java.time.LocalDate;

public class DateCalculation {
    public static String calculateDuration(LocalDate startDate, LocalDate endDate) {
        String start = (startDate.getMonth().toString()).substring(0, 3) + " " + startDate.getYear();
        String end = endDate == null ? "Today" : (endDate.getMonth().toString()).substring(0, 3) + " " + endDate.getYear();
        return start + " - " + end;
    }

    public static String calculateYearDuration(int startYear, int endYear) {
        String startDate = String.valueOf(startYear);
        String endDate = endYear == 0 ? "Today" : String.valueOf(endYear);
        return startDate + " - " + endDate;
    }
}
