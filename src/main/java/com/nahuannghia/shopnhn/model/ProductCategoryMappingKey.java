package com.nahuannghia.shopnhn.model;

import java.io.Serializable;
import java.util.Objects;

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

    // Override equals() để so sánh hai đối tượng
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCategoryMappingKey that = (ProductCategoryMappingKey) o;
        return categoryId == that.categoryId &&
               Objects.equals(productId, that.productId);
    }

    // Override hashCode() để sinh mã băm từ productId và categoryId
    @Override
    public int hashCode() {
        return Objects.hash(productId, categoryId);
    }
}
