package com.hms.profile_service.controller;

import com.hms.profile_service.dto.BranchRequest;
import com.hms.profile_service.dto.BranchResponse;
import com.hms.profile_service.dto.BranchTreeResponse;
import com.hms.profile_service.service.BranchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile/branches")
public class BranchController {

    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @PostMapping
    public ResponseEntity<BranchResponse> create(@RequestBody BranchRequest request, @RequestHeader("X-Auth-UserId") Long creatorId) {
        return ResponseEntity.ok(branchService.create(request, creatorId));
    }

    @GetMapping
    public ResponseEntity<List<BranchResponse>> list() {
        return ResponseEntity.ok(branchService.list());
    }

    @GetMapping("/tree")
    public ResponseEntity<List<BranchTreeResponse>> tree() {
        return ResponseEntity.ok(branchService.getTree());
    }
}

