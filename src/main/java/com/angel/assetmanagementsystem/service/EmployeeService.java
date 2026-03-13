package com.angel.assetmanagementsystem.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.angel.assetmanagementsystem.domain.Department;
import com.angel.assetmanagementsystem.domain.ELocationType;
import com.angel.assetmanagementsystem.domain.Employee;
import com.angel.assetmanagementsystem.domain.Location;
import com.angel.assetmanagementsystem.repository.DepartmentRepository;
import com.angel.assetmanagementsystem.repository.EmployeeRepository;
import com.angel.assetmanagementsystem.repository.LocationRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public String saveEmployee(Employee employee, String villageId, String departmentId) {

        Boolean nationalIdExists = employeeRepository.existsByNationalId(employee.getNationalId());
        if (nationalIdExists) {
            return "Employee with this national ID already exists.";
        }

        Boolean codeExists = employeeRepository.existsByEmployeeCode(employee.getEmployeeCode());
        if (codeExists) {
            return "Employee with this code already exists.";
        }

        
        if (villageId == null) {
            return "Village ID is required for saving an employee.";
        }
        Location village = locationRepository.findById(UUID.fromString(villageId)).orElse(null);
        if (village == null) {
            return "Village not found.";
        }
        if (!village.getType().equals(ELocationType.VILLAGE)) {
            return "The provided location is not a village.";
        }
        employee.setVillage(village);

        
        if (departmentId != null) {
            Department department = departmentRepository.findById(UUID.fromString(departmentId)).orElse(null);
            if (department == null) {
                return "Department not found.";
            }
            employee.setDepartment(department);
        }

        employeeRepository.save(employee);
        return "Employee saved successfully.";
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(String id) {
        return employeeRepository.findById(UUID.fromString(id)).orElse(null);
    }

    public List<Employee> getEmployeesByProvince(String provinceCode, String provinceName) {
        return employeeRepository.findByProvinceNameOrCode(provinceName, provinceCode);
    }
}
