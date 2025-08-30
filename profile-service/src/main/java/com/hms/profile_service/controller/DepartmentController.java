package com.hms.profile_service.controller;

import com.hms.profile_service.model.Department;
import com.hms.profile_service.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping("/create")
    public ResponseEntity<String> createDepartment(@RequestBody Department department) {
       Department departmentCreated =  departmentService.createDepartment(department);
        return ResponseEntity.ok("created" );
    }
}
