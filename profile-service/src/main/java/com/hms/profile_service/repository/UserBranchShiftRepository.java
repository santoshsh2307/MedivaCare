package com.hms.profile_service.repository;

import com.hms.profile_service.model.UserBranchShift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBranchShiftRepository extends JpaRepository<UserBranchShift, Long> {

    List<UserBranchShift> findByUserId(Long userId);

    List<UserBranchShift> findByBranchId(Long branchId);
}

