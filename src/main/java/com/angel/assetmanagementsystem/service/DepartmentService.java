package com.angel.assetmanagementsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.angel.assetmanagementsystem.domain.Department;
import com.angel.assetmanagementsystem.repository.DepartmentRepository;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public String saveDepartment(Department department) {
        Boolean exists = departmentRepository.existsByName(department.getName());
        if (exists) {
            return "Department with this name already exists.";
        }
        departmentRepository.save(department);
        return "Department saved successfully.";
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(String id) {
        return departmentRepository.findById(java.util.UUID.fromString(id)).orElse(null);
    }
}
