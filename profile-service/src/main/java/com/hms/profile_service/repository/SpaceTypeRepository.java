package com.hms.profile_service.repository;

import com.hms.profile_service.model.SpaceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceTypeRepository extends JpaRepository<SpaceType,Long> {
    SpaceType save(SpaceType spaceType);
}
