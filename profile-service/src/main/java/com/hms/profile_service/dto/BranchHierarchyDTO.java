package com.hms.profile_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class BranchHierarchyDTO {

    private Long branchId;
    private String branchName;
    private String location;
    private String status;
    private String type;
    private String parentBranchName;

    private List<SpaceDTO> spaces;
    private List<UserProfileDTO> users;
}
