package com.hms.profile_service.DTO;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserOnBoardingDTO {
    // ===== User Info =====
    private Long userId;
    private String username;
    private String password;
    private boolean enabled;
    private Set<String> roles;  // Only role names
//    private Set<AddressDTO> addresses;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLoginAt;
    private String phoneNumber;
    private String emergencyContactNumber;
    private String photo;


    private Long professionalInfoId;
    private String medicalRegistrationNumber;
    private Long departmentId;
    private String qualification;
    private int yearsOfExperience;
    private LocalDate professionalCreatedAt;
    private boolean isActive;
    private String specialization;


    private String name;
}

