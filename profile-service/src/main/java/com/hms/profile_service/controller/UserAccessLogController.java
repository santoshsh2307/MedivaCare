package com.hms.profile_service.controller;

import com.hms.profile_service.model.UserAccessLog;
import com.hms.profile_service.service.UserAccessLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/profile/access-log")
public class UserAccessLogController {

    private final UserAccessLogService accessLogService;

    public UserAccessLogController(UserAccessLogService accessLogService) {
        this.accessLogService = accessLogService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserAccessLog>> getUserAccessLog(@PathVariable Long userId) {
        return ResponseEntity.ok(accessLogService.getUserAccessLog(userId));
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<UserAccessLog> logEmergencyAccess(@PathVariable Long userId, @RequestBody Map<String,String> req) {
        return ResponseEntity.ok(accessLogService.logAccess(userId, req.get("reason"), "EMERGENCY"));
    }
}

