package com.angel.assetmanagementsystem.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.angel.assetmanagementsystem.domain.AssetCategory;

@Repository
public interface AssetCategoryRepository extends JpaRepository<AssetCategory, UUID> {

    Boolean existsByName(String name);
}
