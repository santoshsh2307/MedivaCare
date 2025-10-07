package com.hms.profile_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "visits")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to the Patient entity (Many visits can belong to one patient)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "reason_for_visit", length = 255, nullable = false)
    private String reasonForVisit;

    @Column(name = "visit_date", nullable = false)
    private LocalDate visitDate;

    @Column(name = "visit_time", nullable = false)
    private LocalTime visitTime;

    // Department and Doctor represented by IDs (from other services/tables)
    @Column(name = "department_id", nullable = false)
    private Long departmentId;

    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;

    @Column(name = "visit_type", length = 50, nullable = false)
    private String visitType;  // Example: "Consultation", "Follow-up", "Emergency"

    @Column(name = "source_type", length = 50)
    private String sourceType; // Example: "Referral", "Walk-in", "Online"

    @Column(name = "ref_doctor_name", length = 100)
    private String refDoctorName; // Referring doctor (if any)
}

