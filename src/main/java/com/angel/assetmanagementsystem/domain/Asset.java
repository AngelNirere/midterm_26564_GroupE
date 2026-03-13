package com.angel.assetmanagementsystem.domain;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "assets")
public class Asset {

    @Id
    @Column(name = "asset_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "asset_code", unique = true)
    private String assetCode;

    @Column(name = "name")
    private String name;

    @Column(name = "serial_number", unique = true)
    private String serialNumber;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "purchase_price")
    private Double purchasePrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EAssetStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition")
    private EAssetCondition condition;

    
    @ManyToOne
    @JoinColumn(name = "category_id")
    private AssetCategory category;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "detail_id", referencedColumnName = "detail_id")
    private AssetDetail detail;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAssetCode() {
        return this.assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public LocalDate getPurchaseDate() {
        return this.purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Double getPurchasePrice() {
        return this.purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public EAssetStatus getStatus() {
        return this.status;
    }

    public void setStatus(EAssetStatus status) {
        this.status = status;
    }

    public EAssetCondition getCondition() {
        return this.condition;
    }

    public void setCondition(EAssetCondition condition) {
        this.condition = condition;
    }

    public AssetCategory getCategory() {
        return this.category;
    }

    public void setCategory(AssetCategory category) {
        this.category = category;
    }

    public AssetDetail getDetail() {
        return this.detail;
    }

    public void setDetail(AssetDetail detail) {
        this.detail = detail;
    }
}
