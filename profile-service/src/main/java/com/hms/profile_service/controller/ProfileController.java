package com.hms.profile_service.controller;

import com.hms.profile_service.dto.ApiResponse;
import com.hms.profile_service.dto.StaffOnboardingRequest;
import com.hms.profile_service.dto.StaffOnboardingResponse;
import com.hms.profile_service.exception.DuplicateResourceException;
import com.hms.profile_service.model.User;
import com.hms.profile_service.service.StaffOnboardingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {



    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/me")
    public ResponseEntity<String> getProfile(@RequestHeader("X-Auth-Username") String username) {
        return ResponseEntity.ok("Hello " + username + ", this is your profile data.");
    }

    private final StaffOnboardingService staffOnboardingService;

    public ProfileController(StaffOnboardingService staffOnboardingService) {
        this.staffOnboardingService = staffOnboardingService;
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

}


