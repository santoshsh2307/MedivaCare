package com.hms.profile_service.serviceImpl;

import com.hms.profile_service.model.Department;
import com.hms.profile_service.repository.DepartmentRepository;
import com.hms.profile_service.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;
    @Override
    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }
}
