package com.hms.profile_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffOnboardingRequest {

    private PersonalInfo personalInfo;
    private ProfessionalInfo professionalInfo;
    private ComplianceAndDocs complianceAndDocs;
    private SystemAccess systemAccess; // <-- new
    private ReviewAndConsent reviewAndConsent;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SystemAccess {
        private String username;
        private List<Long> roleIds;
        private Boolean isActive;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PersonalInfo {
        private String fullName;
        private String email;
        private String phone;
        private LocalDate dob;
        private String gender;
        private String address;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProfessionalInfo {
        private String employeeId;
        private String designation;
        private String department;
        private LocalDate joiningDate;
        private Integer experienceYears;
        private String qualification;
        private String specialization;
        private String shift;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ComplianceAndDocs {
        private String aadharNumber;
        private String panNumber;
        private String medicalLicenseNo;
        private EmergencyContact emergencyContact;
        private List<Document> documents;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmergencyContact {
        private String name;
        private String relation;
        private String phone;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Document {
        private String type;
        private String fileUrl;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewAndConsent {
        private Boolean termsAccepted;
        private LocalDateTime createdAt;
    }
}

