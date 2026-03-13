package com.angel.assetmanagementsystem.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.angel.assetmanagementsystem.domain.Asset;

@Repository
public interface AssetRepository extends JpaRepository<Asset, UUID> {

    Boolean existsBySerialNumber(String serialNumber);

    Boolean existsByAssetCode(String assetCode);
}
