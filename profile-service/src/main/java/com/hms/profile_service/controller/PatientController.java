package com.hms.profile_service.controller;

import com.hms.profile_service.dto.PatientRegistrationRequest;
import com.hms.profile_service.model.Patient;
import com.hms.profile_service.repository.ApiResponse;
import com.hms.profile_service.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile/patient")
public class PatientController {

    @Autowired
    PatientService patientService;

    @PostMapping("/patients/register")
    public ResponseEntity<ApiResponse<?>> registerPatient(@RequestBody PatientRegistrationRequest request) {
        Patient savedPatient = patientService.registerPatientWithVisit(request);
        ApiResponse<Patient> response = ApiResponse.<Patient>builder()
                .code("200")
                .message("Patient and Visit information saved successfully")
                .content(savedPatient)
                .build();

        return ResponseEntity.ok(response);
    }

}
