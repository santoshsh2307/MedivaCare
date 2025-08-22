package com.hms.profile_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class DoctorDetailsDTO {
    private Long id;
    private String name;
    private String phone;
    private String deptName;
    private String education;
    private int yearsOfExp;
    private String specialization;
    private String licence;
    private java.time.LocalDate createdAt;
    private boolean status;

    public DoctorDetailsDTO(Long id, String name, String phone, String deptName, String education, int yearsOfExp, String specialization, String licence, LocalDate createdAt, boolean status) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.deptName = deptName;
        this.education = education;
        this.yearsOfExp = yearsOfExp;
        this.specialization = specialization;
        this.licence = licence;
        this.createdAt = createdAt;
        this.status = status;
    }
}
