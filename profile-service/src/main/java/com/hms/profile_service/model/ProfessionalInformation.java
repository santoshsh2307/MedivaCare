package com.hms.profile_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "professional_information",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "medical_registration_number")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfessionalInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "medical_registration_number", nullable = false, unique = true, length = 100)
    private String medicalRegistrationNumber;

    @Column(name = "department_id", nullable = false)
    private Long departmentId;   // Just store dept_id instead of mapping

    @Column(nullable = false, length = 150)
    private String qualification;

    @Column(name = "years_of_experience", nullable = false)
    private Integer yearsOfExperience;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.isActive == null) {
            this.isActive = true; // default active
        }
    }
}

