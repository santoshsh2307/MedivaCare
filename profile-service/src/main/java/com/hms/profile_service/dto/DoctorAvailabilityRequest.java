package com.hms.profile_service.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorAvailabilityRequest {

    private Long doctorId;

    // Default availability period
    private LocalDate fromDate; // start of availability
    private LocalDate toDate;   // end of availability

    // Default weekly schedule
    private List<String> weekdays; // ["Mon", "Tue"]
    private LocalTime defaultStartTime;
    private LocalTime defaultEndTime;
    private Integer intervalMinutes;

    // Custom times per weekday (optional)
    private Map<String, TimeRange> weekdayTimeRanges;

    // Custom specific dates (optional)
    private List<CustomDateAvailability> customDates;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TimeRange {
        private LocalTime startTime;
        private LocalTime endTime;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CustomDateAvailability {
        private LocalDate date;
        private LocalTime startTime;
        private LocalTime endTime;
    }
}
