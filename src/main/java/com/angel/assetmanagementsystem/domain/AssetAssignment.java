package com.angel.assetmanagementsystem.domain;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "asset_assignments")
public class AssetAssignment {

    @Id
    @Column(name = "assignment_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    
    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;

    @Column(name = "assigned_date")
    private LocalDate assignedDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Asset getAsset() {
        return this.asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public LocalDate getAssignedDate() {
        return this.assignedDate;
    }

    public void setAssignedDate(LocalDate assignedDate) {
        this.assignedDate = assignedDate;
    }

    public LocalDate getReturnDate() {
        return this.returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
