package com.hms.profile_service.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientRegistrationRequest {

    // ========== Patient Information ==========
    private String firstName;
    private String lastName;
    private String patientType;     // In Patient / Out Patient
    private String inOutStatus;     // Optional if same as patientType
    private Integer age;
    private LocalDate dateOfBirth;
    private String gender;
    private String maritalStatus;
    private String mobile;
    private String alternateMobile;
    private String email;
    private String alternateEmail;
    private String bloodGroup;
    private String occupation;
    private String nationalId;
    private String timeZone;
    private String vipStatus;
    private String address;
    private String cityTown;
    private String state;
    private String country;
    private String pinCode;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String relation;

    // ========== Visit Information ==========
    private String reasonForVisit;
    private LocalDate visitDate;
    private LocalTime visitTime;
    private Long departmentId;
    private Long doctorId;
    private String visitType;       // Consultation, Follow-up, etc.
    private String sourceType;      // Walk-in, Referral, etc.
    private String refDoctorName;
}

