package com.hms.profile_service.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "phone_number")
        }
)
@Data
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // System Access
    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean enabled = true;

    // Roles mapping
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    // Address mapping
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Address> addresses = new HashSet<>();

    // Professional Info
    @Column(name = "professional_id")
    private Long professionalId;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "employee_id", length = 50)
    private String employeeId;

    @Column(name = "designation")
    private String designation;

    @Column(name = "joining_date")
    private LocalDate joiningDate;

    @Column(name = "experience_years")
    private Integer experienceYears;

    @Column(name = "qualification")
    private String qualification;

    @Column(name = "specialization")
    private String specialization;

    @Column(name = "shift")
    private String shift;

    // Personal Info
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone_number", nullable = false, unique = true, length = 15)
    private String phoneNumber;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "gender")
    private String gender;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    // Compliance & Docs
    @Column(name = "aadhar_number", length = 20, unique = true)
    private String aadharNumber;

    @Column(name = "pan_number", length = 20, unique = true)
    private String panNumber;

    @Column(name = "medical_license_no", length = 50)
    private String medicalLicenseNo;

    @Column(name = "emergency_contact_name")
    private String emergencyContactName;

    @Column(name = "emergency_contact_relation")
    private String emergencyContactRelation;

    @Column(name = "emergency_contact_number", length = 15)
    private String emergencyContactNumber;

    // Other Info
    private String photo; // Store as URL or relative file path

    @Column(name = "terms_accepted")
    private Boolean termsAccepted;

    // Audit
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
