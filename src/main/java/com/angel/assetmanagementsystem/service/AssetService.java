package com.angel.assetmanagementsystem.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.angel.assetmanagementsystem.domain.Asset;
import com.angel.assetmanagementsystem.domain.AssetCategory;
import com.angel.assetmanagementsystem.repository.AssetCategoryRepository;
import com.angel.assetmanagementsystem.repository.AssetRepository;

@Service
public class AssetService {

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private AssetCategoryRepository assetCategoryRepository;

    public String saveAsset(Asset asset, String categoryId) {

        Boolean assetExists = assetRepository.existsBySerialNumber(asset.getSerialNumber());
        if (assetExists) {
            return "Asset with this serial number already exists.";
        }

        Boolean codeExists = assetRepository.existsByAssetCode(asset.getAssetCode());
        if (codeExists) {
            return "Asset with this code already exists.";
        }

        if (categoryId != null) {
            AssetCategory category = assetCategoryRepository.findById(UUID.fromString(categoryId)).orElse(null);
            if (category == null) {
                return "Asset category not found.";
            }
            asset.setCategory(category);
        }

        assetRepository.save(asset);
        return "Asset saved successfully.";
    }

    
    public Page<Asset> getAllAssets(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return assetRepository.findAll(pageRequest);
    }

    public Asset getAssetById(String id) {
        return assetRepository.findById(UUID.fromString(id)).orElse(null);
    }

    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }
}
