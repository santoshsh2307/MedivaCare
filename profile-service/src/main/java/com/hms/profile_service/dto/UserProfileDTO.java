package com.hms.profile_service.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDTO {

    // --- User fields ---
    private Long userId;
    private String username;
    private boolean enabled;
    private String phoneNumber;
    private String emergencyContactNumber;
    private String photo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLoginAt;

    private Set<Long> roleIds;      // ✅ Role IDs instead of role names
    private Set<String> addresses;  // Or an AddressDTO if needed

    // --- ProfessionalInformation fields ---
    private Long professionalId;
    private String medicalRegistrationNumber;
    private Long departmentId;
    private String qualification;
    private Integer yearsOfExperience;
    private Boolean isActive;
}
