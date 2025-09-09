package com.hms.profile_service.service;

import com.hms.profile_service.dto.DoctorAvailabilityRequest;
import com.hms.profile_service.model.DoctorAvailability;
import com.hms.profile_service.model.User;

import java.util.List;

public interface DoctorAvailabilityService {
    DoctorAvailability saveAvailability(DoctorAvailabilityRequest request);

    List<DoctorAvailability> getAvailabilityByDoctor(Long doctorId);

    List<User> findActiveUsersByRole(String doctor);
}
