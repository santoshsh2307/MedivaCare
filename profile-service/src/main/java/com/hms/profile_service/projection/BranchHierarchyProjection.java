package com.hms.profile_service.projection;

public interface BranchHierarchyProjection {

    Long getBranchId();
    String getBranchName();
    String getLocation();
    String getStatus();
    String getType();
    String getParentBranchName();

    String getSpaces();  // JSON string
    String getUsers();   // JSON string
}
