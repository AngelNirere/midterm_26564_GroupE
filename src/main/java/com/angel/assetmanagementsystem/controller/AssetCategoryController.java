package com.angel.assetmanagementsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.angel.assetmanagementsystem.domain.AssetCategory;
import com.angel.assetmanagementsystem.service.AssetCategoryService;

@RestController
@RequestMapping("/api/categories")
public class AssetCategoryController {

    @Autowired
    private AssetCategoryService assetCategoryService;

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveCategory(@RequestBody AssetCategory category) {
        String response = assetCategoryService.saveCategory(category);

        if (response.equals("Asset category saved successfully.")) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllCategories() {
        List<AssetCategory> categories = assetCategoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
