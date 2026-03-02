package com.hms.profile_service.serviceImpl;

import com.hms.profile_service.model.SpaceType;
import com.hms.profile_service.repository.SpaceTypeRepository;
import com.hms.profile_service.service.SpaceTypeService;
import org.springframework.stereotype.Service;

@Service
public class SpaceTypeServiceImpl implements SpaceTypeService {
    private final SpaceTypeRepository spaceTypeRepository;

    public SpaceTypeServiceImpl(SpaceTypeRepository spaceTypeRepository) {
        this.spaceTypeRepository = spaceTypeRepository;
    }

    @Override
    public SpaceType createSpaceType(SpaceType spaceType) {
        return spaceTypeRepository.save(spaceType);
    }
}
