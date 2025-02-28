package com.nahuannghia.shopnhn.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ProductCategoryMappingKey implements Serializable {
    @Column(length = 30, nullable = false)
    private String productId;  
    @Column(name = "category_id", nullable = false)
    private int categoryId;    
    public ProductCategoryMappingKey() {
    }
    public ProductCategoryMappingKey(String productId, int categoryId) {
        this.productId = productId;
        this.categoryId = categoryId;
    }
    // Getters
    public String getProductId() {
        return productId;
    }
    public int getCategoryId() {
        return categoryId;
    }
    // Setters
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    
}
