package com.hms.profile_service.repository;

import com.hms.profile_service.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface RoleRepository extends JpaRepository<Role,Long> {
}
