package com.angel.assetmanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.angel.assetmanagementsystem.domain.Asset;
import com.angel.assetmanagementsystem.service.AssetService;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveAsset(@RequestBody Asset asset,
                                       @RequestParam(required = false) String categoryId) {
        String response = assetService.saveAsset(asset, categoryId);

        if (response.equals("Asset saved successfully.")) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
    }

    /**
     * Retrieves all assets with pagination and sorting.
     * Query params:
     *   page     - zero-based page number (default: 0)
     *   size     - number of items per page (default: 10)
     *   sortBy   - field name to sort by (default: name)
     *   direction - asc or desc (default: asc)
     */
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllAssets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Page<Asset> assets = assetService.getAllAssets(page, size, sortBy, direction);
        return new ResponseEntity<>(assets, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAssetById(@PathVariable String id) {
        Asset asset = assetService.getAssetById(id);

        if (asset != null) {
            return new ResponseEntity<>(asset, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Asset not found.", HttpStatus.NOT_FOUND);
        }
    }
}
