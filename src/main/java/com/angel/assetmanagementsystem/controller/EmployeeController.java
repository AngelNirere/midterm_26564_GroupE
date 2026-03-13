package com.angel.assetmanagementsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.angel.assetmanagementsystem.domain.Employee;
import com.angel.assetmanagementsystem.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveEmployee(@RequestBody Employee employee,
                                          @RequestParam String villageId,
                                          @RequestParam(required = false) String departmentId) {
        String response = employeeService.saveEmployee(employee, villageId, departmentId);

        if (response.equals("Employee saved successfully.")) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    
    @GetMapping(value = "/byProvince", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEmployeesByProvince(
            @RequestParam(required = false) String provinceCode,
            @RequestParam(required = false) String provinceName) {

        if (provinceCode == null && provinceName == null) {
            return new ResponseEntity<>("Please provide provinceCode or provinceName.", HttpStatus.BAD_REQUEST);
        }

        List<Employee> employees = employeeService.getEmployeesByProvince(
                provinceCode != null ? provinceCode : "",
                provinceName != null ? provinceName : "");

        if (employees.isEmpty()) {
            return new ResponseEntity<>("No employees found for the given province.", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
}
