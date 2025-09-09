package com.hms.profile_service.repository;

import com.hms.profile_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;

import java.util.List;

@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByUsername(String username);
    boolean existsByPhoneNumber(String phoneNumber);
    List<User> findByEnabledTrue();

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :role AND u.enabled = true")
    List<User> findActiveUsersByRole(@Param("role") String role);
}
