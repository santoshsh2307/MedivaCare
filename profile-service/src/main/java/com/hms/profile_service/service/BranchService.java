package com.hms.profile_service.service;

import com.hms.profile_service.dto.BranchHierarchyDTO;
import com.hms.profile_service.dto.BranchRequest;
import com.hms.profile_service.dto.BranchResponse;
import com.hms.profile_service.dto.BranchTreeResponse;

import java.util.List;

public interface BranchService {
    BranchResponse create(BranchRequest request, Long creatorId);
    List<BranchResponse> list();
    List<BranchTreeResponse> getTree();
    public List<BranchResponse> listBranchesForUser(Long userId);

    List<BranchHierarchyDTO> getBranchHierarchy();
}

