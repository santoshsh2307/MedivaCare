package com.hms.profile_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class SpaceDTO {

    private Long spaceId;
    private String spaceName;
    private String spaceType;
    private List<AssetDTO> assets;
}
