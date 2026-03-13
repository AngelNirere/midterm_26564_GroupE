package com.angel.assetmanagementsystem.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.angel.assetmanagementsystem.domain.ELocationType;
import com.angel.assetmanagementsystem.domain.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {

    Boolean existsByCode(String code);

    List<Location> findByType(ELocationType type);

    Optional<Location> findByCodeAndType(String code, ELocationType type);

    Optional<Location> findByNameIgnoreCaseAndType(String name, ELocationType type);

    // Retrieve Village locations whose province (4 levels up) matches by name or code
    @Query("SELECT l FROM Location l WHERE l.type = 'VILLAGE' " +
           "AND (l.parent.parent.parent.parent.name = :provinceName " +
           "OR l.parent.parent.parent.parent.code = :provinceCode)")
    List<Location> findVillagesByProvinceNameOrCode(
            @Param("provinceName") String provinceName,
            @Param("provinceCode") String provinceCode);
}
