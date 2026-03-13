package com.angel.assetmanagementsystem.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.angel.assetmanagementsystem.domain.ELocationType;
import com.angel.assetmanagementsystem.domain.Location;
import com.angel.assetmanagementsystem.repository.LocationRepository;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public String saveChildLocation(Location location, String parentId) {

        if (parentId != null) {
            Location locationParent = locationRepository.findById(UUID.fromString(parentId)).orElse(null);

            if (locationParent == null) {
                return "Parent location not found.";
            } else {
                location.setParent(locationParent);
            }
        }

        if (!location.getType().equals(ELocationType.PROVINCE) && parentId == null) {
            return "Parent location is required for non-province locations.";
        }

        Boolean checkLocation = locationRepository.existsByCode(location.getCode());
        if (checkLocation) {
            return "Location with this code already exists.";
        }

        locationRepository.save(location);
        return "Location saved successfully.";
    }

    
    public Location getLocationById(String id) {
        return locationRepository.findById(UUID.fromString(id)).orElse(null);
    }

    public Object getAllByType(String type) {
        try {
            ELocationType locationType = ELocationType.valueOf(type.toUpperCase());
            return locationRepository.findByType(locationType);
        } catch (IllegalArgumentException e) {
            return "Invalid location type. Valid types: PROVINCE, DISTRICT, SECTOR, CELL, VILLAGE";
        }
    }
}
