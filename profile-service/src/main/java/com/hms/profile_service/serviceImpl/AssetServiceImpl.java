package com.hms.profile_service.serviceImpl;

import com.hms.profile_service.model.Asset;
import com.hms.profile_service.repository.AssetRepository;
import com.hms.profile_service.service.AssetService;
import org.springframework.stereotype.Service;

@Service
public class AssetServiceImpl implements AssetService {
    private final AssetRepository assetRepository;

    public AssetServiceImpl(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    @Override
    public Asset createAsset(Asset asset) {
        return assetRepository.save(asset);
    }
}
