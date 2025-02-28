package com.nahuannghia.shopnhn.model;

import java.io.Serializable;

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
    // getters
    public String getProductId() {
        return productId;
    }
    public String getColor() {
        return colorId;
    }
    // setters
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public void setColor(String colorId) {
        this.colorId = colorId;
    }

    
}
