package com.nahuannghia.shopnhn.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ImageURLProductKey implements Serializable {
    @Column(name = "image_id", length = 30, nullable = false)
    private String imageId;
    private String productId;

    public ImageURLProductKey() {
    }

    public ImageURLProductKey(String imageId, String productId) {
        this.imageId = imageId;
        this.productId = productId;
    }

    // Getters
    public String getImageId() {
        return imageId;
    }

    public String getProductId() {
        return productId;
    }

    // Setters
    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    // Override equals() để so sánh giá trị của 2 đối tượng
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageURLProductKey that = (ImageURLProductKey) o;
        return Objects.equals(imageId, that.imageId) &&
               Objects.equals(productId, that.productId);
    }

    // Override hashCode() để sinh mã băm dựa trên giá trị của các trường
    @Override
    public int hashCode() {
        return Objects.hash(imageId, productId);
    }
}
