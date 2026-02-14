package com.hms.profile_service.service;

import com.hms.profile_service.model.UserAccessLog;

import java.util.List;

public interface UserAccessLogService {

    List<UserAccessLog> getUserAccessLog(Long userId);

    UserAccessLog logAccess(Long userId, String reason, String type);
}
