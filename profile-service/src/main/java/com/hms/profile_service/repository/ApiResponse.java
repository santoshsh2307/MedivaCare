package com.hms.profile_service.repository;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {

    private String code;      // e.g. "200", "400", "500"
    private String message;   // e.g. "Success", "Validation Failed"
    private T content;        // Actual data (generic type)
}

