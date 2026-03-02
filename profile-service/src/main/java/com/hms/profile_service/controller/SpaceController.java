package com.hms.profile_service.controller;

import com.hms.profile_service.model.Branch;
import com.hms.profile_service.model.Space;
import com.hms.profile_service.service.BranchService;
import com.hms.profile_service.service.SpaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/space")
public class SpaceController {


    private final SpaceService spaceService;

    public SpaceController(SpaceService spaceService) {
        this.spaceService = spaceService;
    }
    @PostMapping
    public ResponseEntity<Space> createBranch(@RequestBody Space space) {
        return ResponseEntity.ok(spaceService.createSpace(space));
    }
}
