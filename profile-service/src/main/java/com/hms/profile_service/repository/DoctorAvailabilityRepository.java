package com.hms.profile_service.repository;

import com.hms.profile_service.model.DoctorAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability, Long> {
    List<DoctorAvailability> findByDoctorId(Long doctorId);
}

