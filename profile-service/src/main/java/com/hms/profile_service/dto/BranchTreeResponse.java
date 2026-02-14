package com.hms.profile_service.dto;

import java.util.ArrayList;
import java.util.List;

public class BranchTreeResponse {

    private Long id;
    private String name;
    private String type;
    private String status;
    private String location;

    private List<BranchTreeResponse> children = new ArrayList<>();

    // --- getters & setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<BranchTreeResponse> getChildren() {
        return children;
    }

    public void setChildren(List<BranchTreeResponse> children) {
        this.children = children;
    }
}

