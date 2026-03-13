package com.angel.assetmanagementsystem.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.angel.assetmanagementsystem.domain.AssetDetail;

@Repository
public interface AssetDetailRepository extends JpaRepository<AssetDetail, UUID> {
}
