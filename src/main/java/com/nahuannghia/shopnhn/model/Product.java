package com.nahuannghia.shopnhn.model;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
@Entity
@Table(name = "[product]")

public class Product {
    
    @Id
    @Column(name = "product_id",length = 30, nullable = false)
    private String productId;
    
    @Column(name = "product_name", length = 255, nullable = false)
    private String productName;
    
    @Column(name = "product_description", length = 2000)
    private String productDescription;
    
    @Column(name = "image_url_id", length = 255)
    private String imageUrlId;
    
    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;
    
    @Column( name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;
    
    @Column( name = "discount", precision = 5, scale = 2)
    private BigDecimal discount;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public Product() {
    }
    public Product(String productId, String productName, String productDescription, String imageUrlId, Brand brand, BigDecimal price, BigDecimal discount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.imageUrlId = imageUrlId;
        this.brand = brand;
        this.price = price;
        this.discount = discount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    // Getters
    public String getProductId() {
        return productId;
    }
    public String getProductName() {
        return productName;
    }
    public String getProductDescription() {
        return productDescription;
    }
    public String getImageUrlId() {
        return imageUrlId;
    }
    public Brand getBrand() {
        return brand;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public BigDecimal getDiscount() {
        return discount;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    // Setters
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
    public void setImageUrlId(String imageUrlId) {
        this.imageUrlId = imageUrlId;
    }
    public void setBrand(Brand brand) {
        this.brand = brand;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}

