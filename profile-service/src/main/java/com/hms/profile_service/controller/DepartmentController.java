package com.hms.profile_service.controller;

import com.hms.profile_service.entity.Department;
import com.hms.profile_service.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/profile/api")
public class DepartmentController {

@Autowired
private DepartmentService departmentService;


    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping("/save")
    public ResponseEntity<Department> saveDepartment(@RequestBody Department department) {
        Department savedDepartment = departmentService.save(department);
        return ResponseEntity.ok(savedDepartment);
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        Optional<Department> department = departmentService.findById(id);
        return department.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER',")
    @GetMapping("/getAllDepartment")
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.findAll();
        return ResponseEntity.ok(departments);
    }
}

