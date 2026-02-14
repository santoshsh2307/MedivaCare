package com.hms.profile_service.controller;

import com.hms.profile_service.dto.UserBranchShiftRequest;
import com.hms.profile_service.model.UserBranchShift;
import com.hms.profile_service.service.UserBranchShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile/shifts")
public class UserBranchShiftController {

    private final UserBranchShiftService shiftService;

    public UserBranchShiftController(UserBranchShiftService shiftService) {
        this.shiftService = shiftService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserBranchShift>> getUserShifts(@PathVariable Long userId) {
        return ResponseEntity.ok(shiftService.getUserShifts(userId));
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<UserBranchShift> submitShiftRequest(
            @PathVariable Long userId,
            @RequestBody UserBranchShiftRequest request) {

        return ResponseEntity.ok(shiftService.submitShiftRequest(userId, request));
    }

    @PutMapping("/{shiftId}/approve")
    public ResponseEntity<UserBranchShift> approveShift(@PathVariable Long shiftId) {
        return ResponseEntity.ok(shiftService.approveShift(shiftId));
    }

    @PutMapping("/{shiftId}/reject")
    public ResponseEntity<UserBranchShift> rejectShift(@PathVariable Long shiftId) {
        return ResponseEntity.ok(shiftService.rejectShift(shiftId));
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<UserBranchShift>> getBranchShifts(@PathVariable Long branchId) {
        return ResponseEntity.ok(shiftService.getBranchShifts(branchId));
    }
}

