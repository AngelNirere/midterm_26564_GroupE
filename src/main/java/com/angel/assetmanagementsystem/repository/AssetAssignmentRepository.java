package com.angel.assetmanagementsystem.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.angel.assetmanagementsystem.domain.AssetAssignment;

@Repository
public interface AssetAssignmentRepository extends JpaRepository<AssetAssignment, UUID> {

    List<AssetAssignment> findByEmployeeId(UUID employeeId);

    List<AssetAssignment> findByAssetId(UUID assetId);
}
