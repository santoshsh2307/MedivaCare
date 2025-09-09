package com.hms.profile_service.controller;

import com.hms.profile_service.dto.ApiResponse;
import com.hms.profile_service.dto.DoctorAvailabilityRequest;
import com.hms.profile_service.dto.StaffOnboardingRequest;
import com.hms.profile_service.dto.StaffOnboardingResponse;
import com.hms.profile_service.exception.DuplicateResourceException;
import com.hms.profile_service.model.DoctorAvailability;
import com.hms.profile_service.model.User;
import com.hms.profile_service.service.DoctorAvailabilityService;
import com.hms.profile_service.service.StaffOnboardingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/profile")
public class ProfileController {



    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/me")
    public ResponseEntity<String> getProfile(@RequestHeader("X-Auth-Username") String username) {
        return ResponseEntity.ok("Hello " + username + ", this is your profile data.");
    }

    private final StaffOnboardingService staffOnboardingService;
    private final DoctorAvailabilityService doctorAvailabilityService;


    public ProfileController(StaffOnboardingService staffOnboardingService, DoctorAvailabilityService doctorAvailabilityService) {
        this.staffOnboardingService = staffOnboardingService;
        this.doctorAvailabilityService = doctorAvailabilityService;
    }

   // @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping("/onboarding")
    public Mono<ResponseEntity<ApiResponse<StaffOnboardingResponse>>> onboarding(
            @RequestHeader("X-Auth-Username") String username,
            @RequestBody StaffOnboardingRequest request) {

        return staffOnboardingService.onboard(username, request)
                .map(response -> ResponseEntity.ok(
                        new ApiResponse<>("SUCCESS", "Onboarding completed", response)
                ))
                .onErrorResume(IllegalArgumentException.class, ex ->
                        Mono.just(ResponseEntity.badRequest().body(
                                new ApiResponse<>("ERROR", ex.getMessage(), null)
                        ))
                )
                .onErrorResume(Exception.class, ex ->
                        Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                                new ApiResponse<>("ERROR", "Unexpected error: " + ex.getMessage(), null)
                        ))
                );
    }

    //@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/active")
    public Mono<ApiResponse<List<User>>> getAllActiveUsers(@RequestHeader("X-Auth-Username") String username) {
        return staffOnboardingService.getAllActiveUsers()
                .collectList()
                .map(users -> new ApiResponse<>("SUCCESS", "Users fetched", users))
                .onErrorResume(ex -> Mono.just(
                        new ApiResponse<>("ERROR", "Failed: " + ex.getMessage(), null)
                ));
    }

    // 1. Save availability
    @PostMapping("/createAvailability")
    public ResponseEntity<?> createAvailability(@RequestBody DoctorAvailabilityRequest request) {
        try {
            DoctorAvailability saved = doctorAvailabilityService.saveAvailability(request);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error saving availability: " + e.getMessage());
        }
    }

    // 2. Get availability by doctor
    @GetMapping("doctorAvailability/{doctorId}")
    public ResponseEntity<?> getAvailabilityByDoctor(@PathVariable Long doctorId) {
        try {
            List<DoctorAvailability> availabilities = doctorAvailabilityService.getAvailabilityByDoctor(doctorId);
            if (availabilities.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(availabilities);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching availability: " + e.getMessage());
        }
    }

    @GetMapping("/doctorDropdown")
    public ResponseEntity<List<Map<String, Object>>> getDoctors() {
        List<User> doctors = doctorAvailabilityService.findActiveUsersByRole("DOCTOR");

        List<Map<String, Object>> response = doctors.stream()
                .map(user -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", user.getId());
                    map.put("name", user.getFullName() + (user.getSpecialization() != null ? " (" + user.getSpecialization() + ")" : ""));
                    return map;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }


//    @GetMapping("/doctorDropdown")
//    public ResponseEntity<?> getDoctors() {
//        // Mock response; later replace with Feign client call to doctor-service
//        return ResponseEntity.ok(List.of(
//                Map.of("id", 1, "name", "Dr. John Doe"),
//                Map.of("id", 2, "name", "Dr. Smith")
//        ));
//    }

}


