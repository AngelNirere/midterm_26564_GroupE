# Asset Management System

A robust, enterprise-grade Spring Boot application for managing company assets, developed as a midterm project for Web Technology (Group E).

## 📋 Table of Contents
- [Project Overview](#project-overview)
- [Tech Stack](#tech-stack)
- [Database Design (ERD)](#database-design-erd)
- [Key Features & Implementation](#key-features--implementation)
- [API Endpoints](#api-endpoints)
- [Setup Instructions](#setup-instructions)
- [Screenshots](#screenshots)
- [Author](#author)

---

## 🚀 Project Overview

This Asset Management System is a comprehensive solution for tracking company assets, managing employees, and maintaining a complete hierarchical location structure for Rwanda. The system demonstrates advanced Spring Boot concepts including complex entity relationships, pagination, sorting, and JPQL queries.

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot 3.4.2**
- **Spring Data JPA / Hibernate**
- **PostgreSQL**
- **Maven**
- **Postman** (for API testing)

---

## 🗄️ Database Design (ERD)

### **Requirement 1: Entity Relationship Diagram with 7 Tables (3 Marks)**

The system implements a relational database with **7 core tables** demonstrating various relationship types:

#### **Tables Overview:**

1. **`locations`** - Self-referential hierarchy for Rwanda's administrative divisions
2. **`departments`** - Company departments
3. **`employees`** - Employee information with department and location references
4. **`asset_categories`** - Categories for asset classification
5. **`assets`** - Main asset information
6. **`asset_details`** - Extended asset information
7. **`asset_assignments`** - Join table for employee-asset assignments

#### **ERD Logic & Relationships:**

```
┌─────────────────┐
│   LOCATIONS     │ (Self-referential)
│─────────────────│
│ id (PK)         │◄──┐
│ code            │   │
│ name            │   │
│ type            │   │
│ parent_id (FK)  │───┘ (Self-reference for hierarchy)
└─────────────────┘
        ▲
        │ (Many-to-One)
        │
┌─────────────────┐         ┌─────────────────┐
│  DEPARTMENTS    │         │   EMPLOYEES     │
│─────────────────│         │─────────────────│
│ id (PK)         │◄────────│ id (PK)         │
│ name            │ (M:1)   │ employee_code   │
│ description     │         │ first_name      │
└─────────────────┘         │ last_name       │
                            │ email           │
                            │ phone_number    │
                            │ national_id     │
                            │ department_id(FK)│
                            │ village_id (FK) │───┐
                            └─────────────────┘   │
                                    │             │
                                    │ (M:M)       │ (M:1 to LOCATIONS)
                                    │             │
                            ┌───────▼───────┐     │
                            │ASSET_ASSIGN.  │     │
                            │───────────────│     │
                            │ id (PK)       │     │
                            │ employee_id(FK)│    │
                            │ asset_id (FK) │     │
                            │ assigned_date │     │
                            │ return_date   │     │
                            │ notes         │     │
                            └───────┬───────┘     │
                                    │             │
                                    │ (M:1)       │
                                    │             │
┌─────────────────┐         ┌───────▼───────┐   │
│ASSET_CATEGORIES │         │    ASSETS     │   │
│─────────────────│         │───────────────│   │
│ id (PK)         │◄────────│ id (PK)       │   │
│ name            │ (M:1)   │ asset_code    │   │
│ description     │         │ name          │   │
└─────────────────┘         │ serial_number │   │
                            │ purchase_date │   │
                            │ purchase_price│   │
                            │ status        │   │
                            │ condition     │   │
                            │ category_id(FK)│  │
                            │ detail_id (FK)│   │
                            └───────┬───────┘   │
                                    │           │
                                    │ (1:1)     │
                                    │           │
                            ┌───────▼───────┐   │
                            │ASSET_DETAILS  │   │
                            │───────────────│   │
                            │ id (PK)       │   │
                            │ manufacturer  │   │
                            │ model         │   │
                            │ warranty_expiry│  │
                            │ notes         │   │
                            └───────────────┘   │
                                                │
                            LOCATIONS ◄──────────┘
```

**Relationship Explanation:**
- **Self-Referential (Location)**: Province → District → Sector → Cell → Village
- **One-to-Many**: Department → Employees, AssetCategory → Assets
- **One-to-One**: Asset ↔ AssetDetail
- **Many-to-Many**: Employees ↔ Assets (via AssetAssignment join table)

---

## 🔑 Key Features & Implementation

### **Requirement 2: Implementation of Saving Location (2 Marks)**

#### **How Location Data is Stored:**

The `Location` entity uses a **self-referential relationship** to create a hierarchical structure:

```java
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    private String code;
    private String name;
    
    @Enumerated(EnumType.STRING)
    private ELocationType type; // PROVINCE, DISTRICT, SECTOR, CELL, VILLAGE
    
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Location parent; // Self-reference to parent location
}
```

#### **How Relationships are Handled:**

**Service Layer Logic (`LocationService.saveChildLocation`):**

1. **Parent Validation**: If a `parentId` is provided, the system fetches the parent location from the database
2. **Hierarchy Enforcement**: Non-province locations MUST have a parent (business rule)
3. **Duplicate Prevention**: Uses `existsByCode()` to prevent duplicate location codes
4. **Cascading Save**: When saving a child location, JPA automatically maintains the foreign key relationship

**Example Flow:**
```
Province (Kigali) → District (Gasabo) → Sector (Remera) → Cell (Bibare) → Village (Akabeza)
```

**Data Seeder Implementation:**
- Automatically seeds all 5 Rwanda provinces on application startup
- Seeds complete Kigali hierarchy (3 districts, 35 sectors, 50+ cells, 100+ villages)
- Uses `existsByCode()` to prevent duplicate seeding on restart

---

### **Requirement 3: Sorting & Pagination Implementation (5 Marks)**

#### **Sorting Implementation:**

The system uses **Spring Data JPA's `Sort` and `Pageable`** interfaces for dynamic sorting:

```java
@Service
public class AssetService {
    public Page<Asset> getAllAssets(int page, int size, String sortBy, String direction) {
        // Create Sort object based on direction
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        
        // Create PageRequest with sorting
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        
        return assetRepository.findAll(pageRequest);
    }
}
```

**How Sorting Works:**
- **Dynamic Field Selection**: `sortBy` parameter allows sorting by any asset field (name, purchaseDate, etc.)
- **Direction Control**: `direction` parameter supports both ascending and descending order
- **Database-Level Sorting**: Spring Data JPA translates this to SQL `ORDER BY` clause
- **Performance**: Sorting happens at database level, not in application memory

**Example API Call:**
```
GET /api/assets/all?page=0&size=5&sortBy=name&direction=asc
```

#### **Pagination Implementation:**

**How Pagination Works:**

1. **PageRequest Creation**: Combines page number, size, and sort criteria
   ```java
   PageRequest pageRequest = PageRequest.of(page, size, sort);
   ```

2. **Database Query Optimization**: 
   - Spring Data JPA generates SQL with `LIMIT` and `OFFSET`
   - Only requested records are fetched from database
   - Example SQL: `SELECT * FROM assets ORDER BY name ASC LIMIT 5 OFFSET 0`

3. **Page Object Response**:
   ```json
   {
     "content": [...],           // Actual data
     "pageable": {
       "pageNumber": 0,
       "pageSize": 5,
       "sort": { "sorted": true }
     },
     "totalElements": 50,        // Total records
     "totalPages": 10,            // Total pages
     "last": false,
     "first": true
   }
   ```

**Performance Benefits:**
- **Reduced Memory Usage**: Only loads requested page into memory
- **Faster Response Time**: Smaller data transfer over network
- **Scalability**: Can handle large datasets efficiently
- **User Experience**: Enables smooth navigation through large result sets

---

### **Requirement 4: Many-to-Many Relationship (3 Marks)**

#### **Implementation: Employee ↔ Asset Assignment**

**Join Table Entity (`AssetAssignment`):**

```java
@Entity
@Table(name = "asset_assignments")
public class AssetAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    
    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;
    
    private LocalDate assignedDate;
    private LocalDate returnDate;
    private String notes;
}
```

**How the Relationship is Mapped:**

1. **Join Table Structure**:
   - `asset_assignments` table acts as the intermediary
   - Contains foreign keys: `employee_id` and `asset_id`
   - Additional fields: `assigned_date`, `return_date`, `notes`

2. **Relationship Mapping**:
   - **Employee Side**: One employee can have multiple asset assignments
   - **Asset Side**: One asset can be assigned to multiple employees over time
   - **Join Table**: Manages the many-to-many relationship with extra metadata

3. **Database Schema**:
   ```sql
   CREATE TABLE asset_assignments (
       assignment_id UUID PRIMARY KEY,
       employee_id UUID REFERENCES employees(employee_id),
       asset_id UUID REFERENCES assets(asset_id),
       assigned_date DATE,
       return_date DATE,
       notes TEXT
   );
   ```

**Why This Approach:**
- Allows tracking assignment history
- Supports multiple assignments per asset
- Stores assignment-specific data (dates, notes)
- Maintains referential integrity through foreign keys

---

### **Requirement 5: One-to-Many Relationship (2 Marks)**

#### **Implementation: Department → Employees**

**Entity Mapping:**

```java
// Department Entity (One side)
@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;
    // No explicit collection of employees (unidirectional)
}

// Employee Entity (Many side)
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department; // Foreign key reference
    
    // Other fields...
}
```

**Relationship Explanation:**

1. **Foreign Key Usage**:
   - `employees` table contains `department_id` column
   - This column references `departments.id`
   - Database enforces referential integrity

2. **Mapping Strategy**:
   - **Unidirectional**: Only Employee knows about Department
   - **@ManyToOne**: Multiple employees belong to one department
   - **@JoinColumn**: Specifies the foreign key column name

3. **Database Constraint**:
   ```sql
   ALTER TABLE employees 
   ADD CONSTRAINT fk_department 
   FOREIGN KEY (department_id) 
   REFERENCES departments(id);
   ```

**Additional One-to-Many Examples in the System:**
- **AssetCategory → Assets**: One category has many assets
- **Location → Employees**: One village has many employees
- **Location → Location**: One parent location has many child locations

---

### **Requirement 6: One-to-One Relationship (2 Marks)**

#### **Implementation: Asset ↔ AssetDetail**

**Entity Mapping:**

```java
// Asset Entity (Owning side)
@Entity
@Table(name = "assets")
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "detail_id", referencedColumnName = "detail_id")
    private AssetDetail detail; // Owns the relationship
    
    // Other fields...
}

// AssetDetail Entity (Inverse side)
@Entity
@Table(name = "asset_details")
public class AssetDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @OneToOne(mappedBy = "detail")
    private Asset asset; // Mapped by the owning side
    
    private String manufacturer;
    private String model;
    private LocalDate warrantyExpiry;
    private String notes;
}
```

**How Entities are Connected:**

1. **Owning Side (Asset)**:
   - Contains the foreign key `detail_id`
   - Uses `@JoinColumn` to specify the column
   - `cascade = CascadeType.ALL` means saving Asset also saves AssetDetail

2. **Inverse Side (AssetDetail)**:
   - Uses `mappedBy = "detail"` to indicate it's the inverse side
   - No foreign key column in this table
   - Bidirectional relationship for easy navigation

3. **Database Structure**:
   ```sql
   CREATE TABLE assets (
       asset_id UUID PRIMARY KEY,
       detail_id UUID UNIQUE REFERENCES asset_details(detail_id),
       -- other columns
   );
   ```

**Why One-to-One:**
- Separates core asset data from extended details
- Improves query performance (can fetch asset without details)
- Allows optional detailed information
- Better database normalization

---

### **Requirement 7: existsBy() Method Implementation (2 Marks)**

#### **How Existence Checking Works in Spring Data JPA:**

**Repository Methods:**

```java
@Repository
public interface AssetRepository extends JpaRepository<Asset, UUID> {
    Boolean existsBySerialNumber(String serialNumber);
    Boolean existsByAssetCode(String assetCode);
}

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {
    Boolean existsByCode(String code);
}

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    Boolean existsByNationalId(String nationalId);
    Boolean existsByEmployeeCode(String employeeCode);
}
```

**How It Works:**

1. **Method Naming Convention**:
   - Spring Data JPA parses method name: `existsBy` + `FieldName`
   - Automatically generates SQL query: `SELECT COUNT(*) > 0 FROM table WHERE field = ?`

2. **Query Execution**:
   ```sql
   -- For existsBySerialNumber("SN12345")
   SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END 
   FROM assets 
   WHERE serial_number = 'SN12345';
   ```

3. **Performance Optimization**:
   - Returns boolean immediately after finding first match
   - More efficient than fetching entire entity
   - Uses database indexes for fast lookup

**Usage in Service Layer:**

```java
@Service
public class AssetService {
    public String saveAsset(Asset asset, String categoryId) {
        // Check if serial number already exists
        Boolean assetExists = assetRepository.existsBySerialNumber(asset.getSerialNumber());
        if (assetExists) {
            return "Asset with this serial number already exists.";
        }
        
        // Check if asset code already exists
        Boolean codeExists = assetRepository.existsByAssetCode(asset.getAssetCode());
        if (codeExists) {
            return "Asset with this code already exists.";
        }
        
        // Proceed with saving...
        assetRepository.save(asset);
        return "Asset saved successfully.";
    }
}
```

**Benefits:**
- **Data Integrity**: Prevents duplicate entries
- **User Feedback**: Provides clear error messages
- **Performance**: Lightweight query compared to full entity fetch
- **Validation**: Enforces business rules at application level

---

### **Requirement 8: Retrieve Users from Province (2 Marks)**

#### **Implementation: JPQL Query with Location Hierarchy Traversal**

**Repository Method:**

```java
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    
    @Query("SELECT e FROM Employee e WHERE " +
           "e.village.parent.parent.parent.parent.name = :provinceName " +
           "OR e.village.parent.parent.parent.parent.code = :provinceCode")
    List<Employee> findByProvinceNameOrCode(
            @Param("provinceName") String provinceName,
            @Param("provinceCode") String provinceCode);
}
```

**Query Logic Explanation:**

1. **Hierarchy Traversal**:
   ```
   Employee → village (VILLAGE)
            → parent (CELL)
            → parent (SECTOR)
            → parent (DISTRICT)
            → parent (PROVINCE)
   ```

2. **JPQL Path Navigation**:
   - `e.village`: Employee's village location
   - `.parent`: Navigate to parent location (Cell)
   - `.parent`: Navigate to parent location (Sector)
   - `.parent`: Navigate to parent location (District)
   - `.parent`: Navigate to parent location (Province)
   - `.name` or `.code`: Match province name or code

3. **Flexible Search**:
   - Accepts either province name ("Kigali") OR province code ("KG")
   - Single query handles both search criteria

**Generated SQL (Simplified):**

```sql
SELECT e.* 
FROM employees e
JOIN locations v ON e.village_id = v.location_id          -- Village
JOIN locations c ON v.parent_id = c.location_id           -- Cell
JOIN locations s ON c.parent_id = s.location_id           -- Sector
JOIN locations d ON s.parent_id = d.location_id           -- District
JOIN locations p ON d.parent_id = p.location_id           -- Province
WHERE p.name = 'Kigali' OR p.code = 'KG';
```

**Usage Example:**

```java
@Service
public class EmployeeService {
    public List<Employee> getEmployeesByProvince(String provinceIdentifier) {
        return employeeRepository.findByProvinceNameOrCode(
            provinceIdentifier,  // Can be "Kigali"
            provinceIdentifier   // Or "KG"
        );
    }
}
```

**API Endpoint:**
```
GET /api/employees/by-province?province=Kigali
GET /api/employees/by-province?province=KG
```

**Why This Approach:**
- **Efficient**: Single query with joins
- **Flexible**: Accepts name or code
- **Type-Safe**: JPQL provides compile-time checking
- **Maintainable**: Clear hierarchy traversal logic

---

## 📡 API Endpoints

### **Locations**
- `POST /api/locations/save?parentId={uuid}` - Save location with parent
- `GET /api/locations/all?type={VILLAGE|CELL|SECTOR|DISTRICT|PROVINCE}` - Get locations by type

### **Departments**
- `POST /api/departments/save` - Create department
- `GET /api/departments/all` - Get all departments

### **Employees**
- `POST /api/employees/save?villageId={uuid}&departmentId={uuid}` - Create employee
- `GET /api/employees/all` - Get all employees
- `GET /api/employees/by-province?province={name|code}` - Get employees by province

### **Asset Categories**
- `POST /api/categories/save` - Create category
- `GET /api/categories/all` - Get all categories

### **Assets**
- `POST /api/assets/save?categoryId={uuid}` - Create asset
- `GET /api/assets/all?page=0&size=5&sortBy=name&direction=asc` - Get assets with pagination and sorting

### **Asset Assignments**
- `POST /api/assignments/save?employeeId={uuid}&assetId={uuid}` - Assign asset to employee
- `GET /api/assignments/all` - Get all assignments

---

## ⚙️ Setup Instructions

### **Prerequisites**
- Java 25 
- PostgreSQL 12 or higher
- Maven 3.6+

### **Installation Steps**

1. **Clone the repository:**
   ```bash
   git clone https://github.com/AngelNirere/midterm_26564_GroupE.git
   cd midterm_26564_GroupE
   ```

2. **Create PostgreSQL Database:**
   ```sql
   CREATE DATABASE asset_management_system_db;
   ```

3. **Configure Database Connection:**
   
   Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/asset_management_system_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```

4. **Run the Application:**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

5. **Verify Data Seeding:**
   
   Check console output for:
   ```
   >>> DataSeeder: Rwanda provinces and full Kigali hierarchy seeded successfully.
   ```

6. **Test API Endpoints:**
   
   Use Postman or any API client:
   ```
   http://localhost:8080/api/employees/all
   http://localhost:8080/api/locations/all?type=VILLAGE
   http://localhost:8080/api/assets/all?page=0&size=5&sortBy=name&direction=asc
   ```

---

## 📸 Screenshots

### 1. Employee Retrieval - Successful API Response
<img width="1037" height="752" alt="Image" src="https://github.com/user-attachments/assets/00d756b1-2e9f-4b18-82d6-ebba65cff403" />
*Successfully retrieving all employees with department and village information*

### 2. Asset Categories - GET Request
<img width="1038" height="768" alt="Image" src="https://github.com/user-attachments/assets/8d1949f2-261a-49c1-92a6-2819e3c07e22" />
*Fetching all asset categories (Laptops, Smart Phones, etc.)*

### 3. Employee Creation - POST Request
![Employee Save](screenshots/employee_save.png)
*Creating a new employee with village and department assignment*

### 4. Departments List
<img width="1048" height="762" alt="Image" src="https://github.com/user-attachments/assets/9d6823d4-1d9f-4b36-803e-da162ab29d21" />
*Retrieving all departments (IT Department, HR Department)*

### 5. Locations by Type - Village Query
<img width="1029" height="767" alt="Image" src="https://github.com/user-attachments/assets/5264f1a2-b625-416b-813f-b8f1bd14b5bd" />
*Querying all village-level locations showing hierarchical structure*

### 6. Asset Category Creation
<img width="1034" height="740" alt="Image" src="https://github.com/user-attachments/assets/8fd81ee3-72c2-4fba-854d-c71a614daffa" />
*Successfully creating a new asset category*

### 7. Assets with Pagination & Sorting
<img width="1051" height="768" alt="Image" src="https://github.com/user-attachments/assets/3dc855d1-7508-48b8-9815-dc3be06f1dee" />
*Demonstrating pagination (page 0, size 5) and sorting by name in ascending order*

---

## 🎯 Key Achievements

✅ **7 Database Tables** with complex relationships  
✅ **Self-Referential Hierarchy** for Rwanda locations  
✅ **Automated Data Seeding** on application startup  
✅ **Pagination & Sorting** for efficient data retrieval  
✅ **JPQL Queries** for deep hierarchy traversal  
✅ **RESTful API Design** with proper HTTP methods  
✅ **Data Validation** using existsBy() methods  
✅ **Complete CRUD Operations** for all entities  

---

## 👨‍💻 Author

**Angel Nirere**  
Student ID: 26564  
Group: E  
Course: Web Technology - Midterm Project  

---

## 📝 License

This project is developed for educational purposes as part of the Web Technology course midterm examination.

---

## 🙏 Acknowledgments

- Spring Boot Documentation
- Rwanda Administrative Divisions Data
- Web Technology Course Instructors

---

**Note**: Replace the screenshot placeholders in the Screenshots section with your actual Postman screenshots by adding them to a `screenshots/` folder in your repository.
