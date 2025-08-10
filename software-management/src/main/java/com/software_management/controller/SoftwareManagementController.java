package com.software_management.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/softwareManagement")
public class SoftwareManagementController {

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/me")
    public ResponseEntity<String> getProfile(@RequestHeader("X-Auth-Username") String username) {
        return ResponseEntity.ok("Hello " + username + ", this is your SoftwareManagement data.");
    }

}


