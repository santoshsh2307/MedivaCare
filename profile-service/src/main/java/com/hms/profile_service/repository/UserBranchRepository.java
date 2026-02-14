package com.hms.profile_service.repository;

import com.hms.profile_service.model.User;
import com.hms.profile_service.model.UserBranch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface UserBranchRepository extends JpaRepository<UserBranch, Long> {
    List<UserBranch> findByUser(User user);
}
