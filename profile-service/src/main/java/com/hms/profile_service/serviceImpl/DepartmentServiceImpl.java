package com.hms.profile_service.serviceImpl;

import com.hms.profile_service.entity.Department;
import com.hms.profile_service.repository.DepartmentRepo;
import com.hms.profile_service.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepo departmentRepo;

    @Override
    public Department save(Department department) {
        return departmentRepo.save(department);
    }

    @Override
    public Optional<Department> findById(Long id) {
        return departmentRepo.findById(id);
    }

    @Override
    public List<Department> findAll() {
        return departmentRepo.findAll();
    }
}
