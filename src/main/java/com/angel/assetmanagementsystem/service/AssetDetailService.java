package com.angel.assetmanagementsystem.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.angel.assetmanagementsystem.domain.AssetDetail;
import com.angel.assetmanagementsystem.repository.AssetDetailRepository;

@Service
public class AssetDetailService {

    @Autowired
    private AssetDetailRepository assetDetailRepository;

    public String saveAssetDetail(AssetDetail assetDetail) {
        assetDetailRepository.save(assetDetail);
        return "Asset detail saved successfully.";
    }

    public List<AssetDetail> getAllAssetDetails() {
        return assetDetailRepository.findAll();
    }

    public AssetDetail getAssetDetailById(String id) {
        return assetDetailRepository.findById(UUID.fromString(id)).orElse(null);
    }
}
