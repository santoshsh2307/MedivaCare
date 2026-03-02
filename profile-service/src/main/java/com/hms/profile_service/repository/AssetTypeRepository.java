package com.hms.profile_service.repository;

import com.hms.profile_service.model.AssetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetTypeRepository extends JpaRepository<AssetType,Long> {
    AssetType save(AssetType assetType);
}
