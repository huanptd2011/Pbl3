package com.nahuannghia.shopnhn.model;

import java.io.Serializable;

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
}
