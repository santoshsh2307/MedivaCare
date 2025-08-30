package com.hms.profile_service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private String status;   // e.g., "SUCCESS", "ERROR"
    private String message;  // e.g., "Onboarding successful"
    private T data;          // Generic payload
}
