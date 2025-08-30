package com.hms.profile_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StaffOnboardingResponse {
    private String message;
    private String username;
}
