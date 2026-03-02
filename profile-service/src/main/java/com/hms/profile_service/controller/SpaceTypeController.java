package com.hms.profile_service.controller;

import com.hms.profile_service.model.SpaceType;
import com.hms.profile_service.service.SpaceTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/space-types")
public class SpaceTypeController {


    private final SpaceTypeService spaceTypeService;

    public SpaceTypeController(SpaceTypeService spaceTypeService) {
        this.spaceTypeService = spaceTypeService;
    }

    @PostMapping
    public ResponseEntity<SpaceType> create(@RequestBody SpaceType spaceType) {
        return ResponseEntity.ok(spaceTypeService.createSpaceType(spaceType));
    }
}
