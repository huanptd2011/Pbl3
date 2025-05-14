package com.nahuannghia.shopnhn.Response;



import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.nahuannghia.shopnhn.model.ProductCategory;

public class ProductResponse {
    private Integer productId;
    public Integer getProductId() {
        return productId;
    }

    private String productName;
    private String productDescription;
    private String brand;
    private BigDecimal price;
    private long totalInventory;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ProductInventoryResponse> sizeColorList;
    private List<ProductImageResponse> imageList;
    private ProductCategory category;
    public ProductResponse() {
    }
   public ProductResponse(Integer productId, String productName, String productDescription, String brand,
            BigDecimal price, Boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt,
            ProductCategory category) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.brand = brand;
        this.price = price;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.category = category;
        this.sizeColorList = new ArrayList<>();
        this.imageList = new ArrayList<>();
    }
    //
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    public ProductResponse(Integer productId, String productName, String productDescription, String brand,
            BigDecimal price, long totalInventory, Boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt,
            List<ProductInventoryResponse> sizeColorList, List<ProductImageResponse> imageList,
            ProductCategory category) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.brand = brand;
        this.price = price;
        this.totalInventory = totalInventory;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.sizeColorList = sizeColorList;
        this.imageList = imageList;
        this.category = category;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductDescription() {
        return productDescription;
    }
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
       public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public List<ProductInventoryResponse> getSizeColorList() {
        return sizeColorList;
    }
    public void setSizeColorList(List<ProductInventoryResponse> sizeColorList) {
        this.sizeColorList = sizeColorList;
    }
    public List<ProductImageResponse> getImageList() {
        return imageList;
    }
    public void setImageList(List<ProductImageResponse> imageList) {
        this.imageList = imageList;
    }
    //
    public long gettotalInventory() {
        return totalInventory;
    }
    public void settotalInventory(long totalInventory) {
        this.totalInventory = totalInventory;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }
}

