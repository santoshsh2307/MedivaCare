package com.hms.profile_service.service.impl;

import com.hms.profile_service.model.UserAccessLog;
import com.hms.profile_service.model.User;
import com.hms.profile_service.repository.UserAccessLogRepository;
import com.hms.profile_service.repository.UserRepository;
import com.hms.profile_service.service.UserAccessLogService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserAccessLogServiceImpl implements UserAccessLogService {

    private final UserAccessLogRepository accessLogRepository;
    private final UserRepository userRepository;

    public UserAccessLogServiceImpl(UserAccessLogRepository accessLogRepository, UserRepository userRepository) {
        this.accessLogRepository = accessLogRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<UserAccessLog> getUserAccessLog(Long userId) {
        return accessLogRepository.findByUserId(userId);
    }

    @Override
    public UserAccessLog logAccess(Long userId, String reason, String type) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) throw new RuntimeException("User not found");

        UserAccessLog log = new UserAccessLog();
        log.setUser(userOpt.get());
        //log.setAccessType(type);
        log.setReason(reason);
        log.setAccessTime(LocalDateTime.now());

        return accessLogRepository.save(log);
    }
}
