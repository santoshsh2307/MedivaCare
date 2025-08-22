package com.hms.profile_service.service;

import com.hms.profile_service.entity.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    Department save(Department department);

    Optional<Department> findById(Long id);

    List<Department> findAll();
}
