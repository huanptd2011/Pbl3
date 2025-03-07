package com.nahuannghia.shopnhn.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ProductSizeKey implements Serializable {
    @Column(length = 30, nullable = false)
    private String productId;

    @Column(name = "size", length = 5, nullable = false)
    private String size;

    public ProductSizeKey() {
    }

    public ProductSizeKey(String productId, String size) {
        this.productId = productId;
        this.size = size;
    }

    // Getters
    public String getProductId() {
        return productId;
    }

    public String getSize() {
        return size;
    }

    // Setters
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setSize(String size) {
        this.size = size;
    }

    // Override equals() để so sánh hai đối tượng
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductSizeKey that = (ProductSizeKey) o;
        return Objects.equals(productId, that.productId) &&
               Objects.equals(size, that.size);
    }

    // Override hashCode() để sinh mã băm từ productId và size
    @Override
    public int hashCode() {
        return Objects.hash(productId, size);
    }
}
