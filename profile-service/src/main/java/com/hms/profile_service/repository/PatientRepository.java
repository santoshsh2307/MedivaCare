package com.hms.profile_service.repository;

import com.hms.profile_service.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    // Example: custom query methods (optional)
    Patient findByMobile(String mobile);

    boolean existsByEmail(String email);
}

