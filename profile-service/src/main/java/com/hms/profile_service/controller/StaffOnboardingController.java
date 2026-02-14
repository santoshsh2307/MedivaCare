package com.hms.profile_service.controller;

import com.hms.profile_service.dto.StaffOnboardingRequest;
import com.hms.profile_service.dto.StaffOnboardingResponse;
import com.hms.profile_service.model.User;
import com.hms.profile_service.repository.ApiResponse;
import com.hms.profile_service.service.StaffOnboardingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/profile/onboarding")
public class StaffOnboardingController {

    private final StaffOnboardingService staffOnboardingService;

    public StaffOnboardingController(StaffOnboardingService staffOnboardingService) {
        this.staffOnboardingService = staffOnboardingService;
    }

    @PostMapping
    public Mono<ResponseEntity<ApiResponse<StaffOnboardingResponse>>> onboard(
            @RequestHeader("X-Auth-Username") String username,
            @RequestBody StaffOnboardingRequest request) {

        return staffOnboardingService.onboard(username, request)
                .map(res -> ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Onboarding completed", res)))
                .onErrorResume(ex -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse<>("ERROR", ex.getMessage(), null))));
    }

    @GetMapping("/active")
    public Mono<ApiResponse<List<User>>> getAllActiveUsers() {
        return staffOnboardingService.getAllActiveUsers()
                .collectList()
                .map(users -> new ApiResponse<>("SUCCESS", "Users fetched", users));
    }
}

