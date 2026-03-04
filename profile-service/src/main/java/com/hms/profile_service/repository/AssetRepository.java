package com.hms.profile_service.repository;

import com.hms.profile_service.model.Asset;
import com.hms.profile_service.serviceImpl.AssetServiceImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends JpaRepository<Asset,Long> {
    Asset save(Asset asset);
}
