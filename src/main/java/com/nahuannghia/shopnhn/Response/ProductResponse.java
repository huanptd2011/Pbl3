package com.nahuannghia.shopnhn.Response;



import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ProductResponse {
    private Integer productId;
    private String productName;
    private String productDescription;
    private String brand;
    private BigDecimal price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ProductInventoryResponse> sizeColorList;
    private List<ProductImageResponse> imageList;
    public ProductResponse(Integer productId, String productName, String productDescription, String brand, BigDecimal price,
                           LocalDateTime createdAt, LocalDateTime updatedAt, List<ProductInventoryResponse> sizeColorList,
                           List<ProductImageResponse> imageList) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.brand = brand;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.sizeColorList = sizeColorList;
        this.imageList = imageList;
    }
    public ProductResponse() {
    }
    public Integer getProductId() {
        return productId;
    }
    public void setProductId(Integer productId) {
        this.productId = productId;
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
}

