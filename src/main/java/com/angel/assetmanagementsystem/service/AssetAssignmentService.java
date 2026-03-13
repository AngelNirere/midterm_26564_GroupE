package com.angel.assetmanagementsystem.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.angel.assetmanagementsystem.domain.Asset;
import com.angel.assetmanagementsystem.domain.AssetAssignment;
import com.angel.assetmanagementsystem.domain.EAssetStatus;
import com.angel.assetmanagementsystem.domain.Employee;
import com.angel.assetmanagementsystem.repository.AssetAssignmentRepository;
import com.angel.assetmanagementsystem.repository.AssetRepository;
import com.angel.assetmanagementsystem.repository.EmployeeRepository;

@Service
public class AssetAssignmentService {

    @Autowired
    private AssetAssignmentRepository assetAssignmentRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    
    public String assignAsset(String employeeId, String assetId, String notes) {

        Employee employee = employeeRepository.findById(UUID.fromString(employeeId)).orElse(null);
        if (employee == null) {
            return "Employee not found.";
        }

        Asset asset = assetRepository.findById(UUID.fromString(assetId)).orElse(null);
        if (asset == null) {
            return "Asset not found.";
        }

        if (!asset.getStatus().equals(EAssetStatus.AVAILABLE)) {
            return "Asset is not available for assignment. Current status: " + asset.getStatus();
        }

        AssetAssignment assignment = new AssetAssignment();
        assignment.setEmployee(employee);
        assignment.setAsset(asset);
        assignment.setAssignedDate(LocalDate.now());
        assignment.setNotes(notes);

        
        asset.setStatus(EAssetStatus.ASSIGNED);
        assetRepository.save(asset);

        assetAssignmentRepository.save(assignment);
        return "Asset assigned to employee successfully.";
    }

    public String returnAsset(String assignmentId) {
        AssetAssignment assignment = assetAssignmentRepository.findById(UUID.fromString(assignmentId)).orElse(null);
        if (assignment == null) {
            return "Assignment record not found.";
        }

        assignment.setReturnDate(LocalDate.now());

        Asset asset = assignment.getAsset();
        asset.setStatus(EAssetStatus.AVAILABLE);
        assetRepository.save(asset);

        assetAssignmentRepository.save(assignment);
        return "Asset returned successfully.";
    }

    public List<AssetAssignment> getAssignmentsByEmployee(String employeeId) {
        return assetAssignmentRepository.findByEmployeeId(UUID.fromString(employeeId));
    }

    public List<AssetAssignment> getAssignmentsByAsset(String assetId) {
        return assetAssignmentRepository.findByAssetId(UUID.fromString(assetId));
    }

    public List<AssetAssignment> getAllAssignments() {
        return assetAssignmentRepository.findAll();
    }
}
