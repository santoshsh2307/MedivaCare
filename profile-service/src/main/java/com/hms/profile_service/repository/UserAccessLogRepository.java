package com.hms.profile_service.repository;

import com.hms.profile_service.model.UserAccessLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAccessLogRepository extends JpaRepository<UserAccessLog, Long> {
    List<UserAccessLog> findByUserId(Long userId);
}
