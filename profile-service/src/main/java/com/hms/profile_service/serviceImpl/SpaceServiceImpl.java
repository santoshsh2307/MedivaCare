package com.hms.profile_service.serviceImpl;

import com.hms.profile_service.model.Space;
import com.hms.profile_service.repository.SpaceRepository;
import com.hms.profile_service.service.SpaceService;
import org.springframework.stereotype.Service;

@Service
public class SpaceServiceImpl implements SpaceService {

    private final SpaceRepository spaceRepository;

    public SpaceServiceImpl(SpaceRepository spaceRepository) {
        this.spaceRepository = spaceRepository;
    }


    @Override
    public Space createSpace(Space space) {
        return spaceRepository.save(space);
    }
}
