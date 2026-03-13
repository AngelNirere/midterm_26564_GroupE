package com.angel.assetmanagementsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.angel.assetmanagementsystem.domain.AssetAssignment;
import com.angel.assetmanagementsystem.service.AssetAssignmentService;

@RestController
@RequestMapping("/api/assignments")
public class AssetAssignmentController {

    @Autowired
    private AssetAssignmentService assetAssignmentService;

    /**
     * Assigns an asset to an employee (Many-to-Many relationship).
     * Creates a row in the asset_assignments join table.
     */
    @PostMapping(value = "/assign", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> assignAsset(@RequestParam String employeeId,
                                         @RequestParam String assetId,
                                         @RequestParam(required = false) String notes) {
        String response = assetAssignmentService.assignAsset(employeeId, assetId, notes);

        if (response.equals("Asset assigned to employee successfully.")) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/return/{assignmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> returnAsset(@PathVariable String assignmentId) {
        String response = assetAssignmentService.returnAsset(assignmentId);

        if (response.equals("Asset returned successfully.")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/employee/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAssignmentsByEmployee(@PathVariable String employeeId) {
        List<AssetAssignment> assignments = assetAssignmentService.getAssignmentsByEmployee(employeeId);

        if (assignments.isEmpty()) {
            return new ResponseEntity<>("No assignments found for this employee.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(assignments, HttpStatus.OK);
    }

    @GetMapping(value = "/asset/{assetId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAssignmentsByAsset(@PathVariable String assetId) {
        List<AssetAssignment> assignments = assetAssignmentService.getAssignmentsByAsset(assetId);

        if (assignments.isEmpty()) {
            return new ResponseEntity<>("No assignments found for this asset.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(assignments, HttpStatus.OK);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllAssignments() {
        List<AssetAssignment> assignments = assetAssignmentService.getAllAssignments();
        return new ResponseEntity<>(assignments, HttpStatus.OK);
    }
}
