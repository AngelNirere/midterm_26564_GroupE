package com.angel.assetmanagementsystem.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.angel.assetmanagementsystem.domain.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, UUID> {

    Boolean existsByName(String name);
}
