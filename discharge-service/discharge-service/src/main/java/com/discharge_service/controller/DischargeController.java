package com.discharge_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/discharge")
public class DischargeController {

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/me")
    public ResponseEntity<String> getProfile(@RequestHeader("X-Auth-Username") String username) {
        return ResponseEntity.ok("Hello " + username + ", this is your discharge data.");
    }

}


