package com.hms.profile_service.repository;

import com.hms.profile_service.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    // 🔹 Get all visits for a specific patient
    List<Visit> findByPatientId(Long patientId);

    // 🔹 Optional: find visits by doctor or department
    List<Visit> findByDoctorId(Long doctorId);

    List<Visit> findByDepartmentId(Long departmentId);
}

