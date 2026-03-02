package com.hms.profile_service.serviceImpl;

import com.hms.profile_service.model.AssetType;
import com.hms.profile_service.repository.AssetTypeRepository;
import com.hms.profile_service.service.AssetTypeService;
import org.springframework.stereotype.Service;

@Service
public class AssetTypeServiceImpl implements AssetTypeService {

    private final AssetTypeRepository assetTypeRepository;

    public AssetTypeServiceImpl(AssetTypeRepository assetTypeRepository) {
        this.assetTypeRepository = assetTypeRepository;
    }

    @Override
    public AssetType createAssetType(AssetType assetType) {
        return assetTypeRepository.save(assetType);
    }
}
