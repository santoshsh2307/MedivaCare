package com.hms.profile_service.exception;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String path;

    public ErrorResponse(int status, String error, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.path = path;
    }

    // getters & setters
}
