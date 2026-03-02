package com.hms.profile_service.controller;

import com.hms.profile_service.model.AssetType;
import com.hms.profile_service.service.AssetTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/asset-types")
public class AssetTypeController {

    private final AssetTypeService assetTypeService;

    public AssetTypeController(AssetTypeService assetTypeService) {
        this.assetTypeService = assetTypeService;
    }

    @PostMapping
    public ResponseEntity<AssetType> create(@RequestBody AssetType assetType) {
        return ResponseEntity.ok(assetTypeService.createAssetType(assetType));
    }
}