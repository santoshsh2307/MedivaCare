package com.hms.profile_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;



    @Entity
    @Table(name = "professional_information")
    @Data
    @RequiredArgsConstructor
    public class ProfessionalInformation {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "medical_registration_number", nullable = false, unique = true)
        private String medicalRegistrationNumber;

        @Column(name = "department_id", nullable = false)
        private Long departmentId;

        @Column(nullable = false)
        private String qualification;

        @Column(name = "years_of_experience", nullable = false)
        private int yearsOfExperience;

        private String specialization;

        @Column(name = "created_at")
        private LocalDate createdAt;

        @Column(name = "is_active")
        private boolean isActive;
    }



