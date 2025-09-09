package com.hms.profile_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "doctor_availability")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long doctorId; // only store doctor ID, no FK

    private LocalDate startDate;
    private LocalDate endDate;

    private LocalTime startTime;
    private LocalTime endTime;

    private Integer intervalMinutes;

    // Store weekdays as comma-separated string, e.g., "MON,TUE,FRI"
    private String weekdays;

    // 1. Optional: Specific dates with custom times
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "availability")
    private List<CustomDateAvailability> customDates;

    // 2. Optional: Per-weekday custom times
    @ElementCollection
    @CollectionTable(name = "doctor_weekday_time", joinColumns = @JoinColumn(name = "availability_id"))
    @MapKeyColumn(name = "weekday") // "MON", "TUE", etc.
    private Map<String, TimeRange> weekdayTimeRanges;

    // ---------------- Nested classes ----------------

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TimeRange {
        private LocalTime startTime;
        private LocalTime endTime;
    }

    @Entity
    @Table(name = "doctor_custom_dates")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CustomDateAvailability {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private LocalDate date;
        private LocalTime startTime;
        private LocalTime endTime;

        @ManyToOne
        @JoinColumn(name = "availability_id")
        private DoctorAvailability availability;
    }
}
