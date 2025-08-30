package com.hms.profile_service.repository;

import com.hms.profile_service.model.ProfessionalInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface ProfessionalRepository extends JpaRepository<ProfessionalInformation, Long> {
}
