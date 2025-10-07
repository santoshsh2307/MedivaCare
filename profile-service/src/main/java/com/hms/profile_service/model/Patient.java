package com.hms.profile_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;

@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "patient_type", nullable = false, length = 50)
    private String patientType; // e.g., "In Patient" / "Out Patient"

    @Column(name = "in_out_status", length = 50)
    private String inOutStatus; // In Patient / Out Patient

    @Column(nullable = false)
    private Integer age;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(length = 10)
    private String gender; // Male / Female / Other

    @Column(name = "marital_status", length = 20)
    private String maritalStatus; // Single / Married / etc.

    @Column(length = 15, nullable = false, unique = true)
    private String mobile;

    @Column(name = "alternate_mobile", length = 15)
    private String alternateMobile;

    @Column(length = 100, unique = true)
    private String email;

    @Column(name = "alternate_email", length = 100)
    private String alternateEmail;

    @Column(name = "blood_group", length = 5)
    private String bloodGroup; // e.g., A+, B-, O+, etc.

    @Column(length = 100)
    private String occupation;

    @Column(name = "national_id", length = 50, unique = true)
    private String nationalId; // Aadhaar / SSN / National ID

    @Column(name = "time_zone", length = 50)
    private String timeZone; // e.g., "Asia/Kolkata"

    @Column(name = "vip_status", length = 10)
    private String vipStatus; // Yes / No

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(name = "city_town", length = 100)
    private String cityTown;

    @Column(length = 100)
    private String state;

    @Column(length = 100)
    private String country;

    @Column(name = "pin_code", length = 10)
    private String pinCode;

    @Column(name = "emergency_contact_name", length = 100)
    private String emergencyContactName;

    @Column(name = "emergency_contact_phone", length = 15)
    private String emergencyContactPhone;

    @Column(length = 50)
    private String relation; // Relationship with patient (Father, Mother, Friend, etc.)
}

