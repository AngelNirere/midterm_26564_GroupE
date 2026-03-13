package com.angel.assetmanagementsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.angel.assetmanagementsystem.domain.AssetDetail;
import com.angel.assetmanagementsystem.service.AssetDetailService;

@RestController
@RequestMapping("/api/asset-details")
public class AssetDetailController {

    @Autowired
    private AssetDetailService assetDetailService;

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveAssetDetail(@RequestBody AssetDetail assetDetail) {
        String response = assetDetailService.saveAssetDetail(assetDetail);
        
        if (response.equals("Asset detail saved successfully.")) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllAssetDetails() {
        List<AssetDetail> details = assetDetailService.getAllAssetDetails();
        return new ResponseEntity<>(details, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAssetDetailById(@PathVariable String id) {
        AssetDetail detail = assetDetailService.getAssetDetailById(id);
        
        if (detail != null) {
            return new ResponseEntity<>(detail, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Asset detail not found.", HttpStatus.NOT_FOUND);
        }
    }
}
