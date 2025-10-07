package com.hms.profile_service.service;

import com.hms.profile_service.dto.PatientRegistrationRequest;
import com.hms.profile_service.model.Patient;
import com.hms.profile_service.model.Visit;
import com.hms.profile_service.repository.PatientRepository;
import com.hms.profile_service.repository.VisitRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final VisitRepository visitRepository;

    @Transactional
    public Patient registerPatientWithVisit(PatientRegistrationRequest req) {

        // 1️⃣ Save Patient info
        Patient patient = Patient.builder()
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .patientType(req.getPatientType())
                .inOutStatus(req.getInOutStatus())
                .age(req.getAge())
                .dateOfBirth(req.getDateOfBirth())
                .gender(req.getGender())
                .maritalStatus(req.getMaritalStatus())
                .mobile(req.getMobile())
                .alternateMobile(req.getAlternateMobile())
                .email(req.getEmail())
                .alternateEmail(req.getAlternateEmail())
                .bloodGroup(req.getBloodGroup())
                .occupation(req.getOccupation())
                .nationalId(req.getNationalId())
                .timeZone(req.getTimeZone())
                .vipStatus(req.getVipStatus())
                .address(req.getAddress())
                .cityTown(req.getCityTown())
                .state(req.getState())
                .country(req.getCountry())
                .pinCode(req.getPinCode())
                .emergencyContactName(req.getEmergencyContactName())
                .emergencyContactPhone(req.getEmergencyContactPhone())
                .relation(req.getRelation())
                .build();

        Patient savedPatient = patientRepository.save(patient);


        // 2️⃣ Save Visit info
        Visit visit = Visit.builder()
                .patient(savedPatient)
                .reasonForVisit(req.getReasonForVisit())
                .visitDate(req.getVisitDate())
                .visitTime(req.getVisitTime())
                .departmentId(req.getDepartmentId())
                .doctorId(req.getDoctorId())
                .visitType(req.getVisitType())
                .sourceType(req.getSourceType())
                .refDoctorName(req.getRefDoctorName())
                .build();

       return savedPatient;
    }
}

