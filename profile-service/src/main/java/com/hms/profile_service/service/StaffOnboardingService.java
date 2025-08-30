package com.hms.profile_service.service;


import com.hms.profile_service.dto.StaffOnboardingRequest;
import com.hms.profile_service.dto.StaffOnboardingResponse;
import com.hms.profile_service.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StaffOnboardingService {
    Mono<StaffOnboardingResponse> onboard(String performedBy, StaffOnboardingRequest request);

    Flux<User> getAllActiveUsers();
}

