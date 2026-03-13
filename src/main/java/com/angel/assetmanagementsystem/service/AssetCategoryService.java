package com.angel.assetmanagementsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.angel.assetmanagementsystem.domain.AssetCategory;
import com.angel.assetmanagementsystem.repository.AssetCategoryRepository;

@Service
public class AssetCategoryService {

    @Autowired
    private AssetCategoryRepository assetCategoryRepository;

    public String saveCategory(AssetCategory category) {
        Boolean exists = assetCategoryRepository.existsByName(category.getName());
        if (exists) {
            return "Asset category with this name already exists.";
        }
        assetCategoryRepository.save(category);
        return "Asset category saved successfully.";
    }

    public List<AssetCategory> getAllCategories() {
        return assetCategoryRepository.findAll();
    }

    public AssetCategory getCategoryById(String id) {
        return assetCategoryRepository.findById(java.util.UUID.fromString(id)).orElse(null);
    }
}
