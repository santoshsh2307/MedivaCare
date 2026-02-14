package com.hms.profile_service.dto;

import com.hms.profile_service.model.Branch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BranchTreeBuilder {

    public static List<BranchTreeResponse> build(List<Branch> branches) {

        Map<Long, BranchTreeResponse> map = new HashMap<>();
        List<BranchTreeResponse> roots = new ArrayList<>();

        for (Branch b : branches) {
            BranchTreeResponse dto = new BranchTreeResponse();
            dto.setId(b.getId());
            dto.setName(b.getBranchName());
            dto.setType(b.getType());
            dto.setStatus(b.getStatus());
            dto.setLocation(b.getLocation());
            map.put(b.getId(), dto);
        }

        for (Branch b : branches) {
            BranchTreeResponse dto = map.get(b.getId());

            if (b.getParent() != null) {
                map.get(b.getParent().getId()).getChildren().add(dto);
            } else {
                roots.add(dto);
            }
        }

        return roots;
    }
}

