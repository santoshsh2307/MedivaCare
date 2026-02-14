package com.hms.profile_service.controller;

import com.hms.profile_service.dto.DoctorAvailabilityRequest;
import com.hms.profile_service.model.DoctorAvailability;
import com.hms.profile_service.service.DoctorAvailabilityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/profile/availability")
public class DoctorAvailabilityController {

    private final DoctorAvailabilityService doctorAvailabilityService;

    public DoctorAvailabilityController(DoctorAvailabilityService doctorAvailabilityService) {
        this.doctorAvailabilityService = doctorAvailabilityService;
    }

    @PostMapping
    public ResponseEntity<DoctorAvailability> createAvailability(@RequestBody DoctorAvailabilityRequest request) {
        return ResponseEntity.ok(doctorAvailabilityService.saveAvailability(request));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<DoctorAvailability>> getAvailabilityByDoctor(@PathVariable Long doctorId) {
        return ResponseEntity.ok(doctorAvailabilityService.getAvailabilityByDoctor(doctorId));
    }

    @GetMapping("/dropdown")
    public ResponseEntity<List<Map<String, Object>>> getDoctorsDropdown() {
        return ResponseEntity.ok(doctorAvailabilityService.getDoctorsDropdown());
    }
}
