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

    private Long doctorId;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    private LocalDate startDate;
    private LocalDate endDate;

    private LocalTime startTime;
    private LocalTime endTime;

    private Integer intervalMinutes; // token duration

    private String weekdays; // "MON,TUE,FRI"

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "availability")
    private List<CustomDateAvailability> customDates;

    @ElementCollection
    @CollectionTable(name = "doctor_weekday_time", joinColumns = @JoinColumn(name = "availability_id"))
    @MapKeyColumn(name = "weekday")
    private Map<String, TimeRange> weekdayTimeRanges;

    private boolean onCall = false; // emergency or on-call flag

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

