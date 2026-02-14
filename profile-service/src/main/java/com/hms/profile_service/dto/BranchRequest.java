package com.hms.profile_service.dto;

import lombok.Data;

@Data
public class BranchRequest {
    private String branchName;
    private String location;
    private String type;
    private Long parentId;
}

