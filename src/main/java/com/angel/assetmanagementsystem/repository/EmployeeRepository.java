package com.angel.assetmanagementsystem.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.angel.assetmanagementsystem.domain.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    Boolean existsByNationalId(String nationalId);

    Boolean existsByEmployeeCode(String employeeCode);

    /**
     * Retrieve all employees from a given province using province code OR province name.
     * The village hierarchy is: village -> cell -> sector -> district -> province
     * So we traverse: e.village (VILLAGE) -> .parent (CELL) -> .parent (SECTOR)
     *                 -> .parent (DISTRICT) -> .parent (PROVINCE)
     */
    @Query("SELECT e FROM Employee e WHERE " +
           "e.village.parent.parent.parent.parent.name = :provinceName " +
           "OR e.village.parent.parent.parent.parent.code = :provinceCode")
    List<Employee> findByProvinceNameOrCode(
            @Param("provinceName") String provinceName,
            @Param("provinceCode") String provinceCode);
}
