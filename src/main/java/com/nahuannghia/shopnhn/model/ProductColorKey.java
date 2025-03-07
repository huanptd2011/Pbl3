package com.nahuannghia.shopnhn.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ProductColorKey implements Serializable {
    @Column(name = "product_id", length = 30, nullable = false)
    private String productId;

    @Column(name = "color_id", length = 30, nullable = false)
    private String colorId;

    public ProductColorKey() {
    }

    public ProductColorKey(String productId, String colorId) {
        this.productId = productId;
        this.colorId = colorId;
    }

    // Getters
    public String getProductId() {
        return productId;
    }

    public String getColorId() {  // Đổi tên cho thống nhất với colorId
        return colorId;
    }

    // Setters
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setColorId(String colorId) { // Đổi tên cho thống nhất với colorId
        this.colorId = colorId;
    }

    // Override equals() để so sánh hai đối tượng
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductColorKey that = (ProductColorKey) o;
        return Objects.equals(productId, that.productId) &&
               Objects.equals(colorId, that.colorId);
    }

    // Override hashCode() để sinh mã băm từ productId và colorId
    @Override
    public int hashCode() {
        return Objects.hash(productId, colorId);
    }
}
