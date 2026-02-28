package com.hms.profile_service.controller;

import com.hms.profile_service.dto.*;
import com.hms.profile_service.model.User;
import com.hms.profile_service.service.BranchService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    /**
     * Get all branches for logged-in user
     */
    @GetMapping("/hierarchy")
    public ResponseEntity<ApiResponse<List<BranchHierarchyDTO>>> getHierarchy(
            Authentication authentication) {

        String username = authentication.getName();  // ✅ safe

        Long userId = 1l;

        List<BranchResponse> branches =
                branchService.listBranchesForUser(userId);

      List<BranchHierarchyDTO> data = branchService.getBranchHierarchy();

        return ResponseEntity.ok(
                new ApiResponse<>("SUCCESS",
                        "Branches fetched successfully",
                        data)
        );
    }
}

