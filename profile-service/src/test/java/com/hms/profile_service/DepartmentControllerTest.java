package com.hms.profile_service;


import com.hms.profile_service.controller.DepartmentController;
import com.hms.profile_service.entity.Department;
import com.hms.profile_service.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepartmentControllerTest {

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    private Department department;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        department = new Department();
        department.setId(1L);
        department.setName("HR");
    }

    @Test
    void testSaveDepartment() {
        when(departmentService.save(department)).thenReturn(department);

        ResponseEntity<Department> response = departmentController.saveDepartment(department);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("HR", response.getBody().getName());
        verify(departmentService, times(1)).save(department);
    }

    @Test
    void testGetDepartmentById_Found() {
        when(departmentService.findById(1L)).thenReturn(Optional.of(department));

        ResponseEntity<Department> response = departmentController.getDepartmentById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("HR", response.getBody().getName());
        verify(departmentService, times(1)).findById(1L);
    }

    @Test
    void testGetDepartmentById_NotFound() {
        when(departmentService.findById(2L)).thenReturn(Optional.empty());

        ResponseEntity<Department> response = departmentController.getDepartmentById(2L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(departmentService, times(1)).findById(2L);
    }

    @Test
    void testGetAllDepartments() {
        List<Department> departments = Arrays.asList(department);
        when(departmentService.findAll()).thenReturn(departments);

        ResponseEntity<List<Department>> response = departmentController.getAllDepartments();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("HR", response.getBody().get(0).getName());
        verify(departmentService, times(1)).findAll();
    }
}

