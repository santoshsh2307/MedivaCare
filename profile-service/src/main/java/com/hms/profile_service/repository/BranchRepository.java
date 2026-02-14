package com.hms.profile_service.repository;

import com.hms.profile_service.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    List<Branch> findByStatus(String status);
}

