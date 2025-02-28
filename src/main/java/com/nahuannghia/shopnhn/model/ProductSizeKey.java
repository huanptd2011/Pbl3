package com.nahuannghia.shopnhn.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ProductSizeKey implements java.io.Serializable {
    @Column(length = 30,nullable = false)
    private String productId;
    @Column(name ="size",length = 5,nullable = false)
    private String size;
    public ProductSizeKey() {
    }
    public ProductSizeKey(String productId, String size) {
        this.productId = productId;
        this.size = size;
    }
    // getters
    public String getProductId() {
        return productId;
    }
    public String getSize() {
        return size;
    }
    // setters
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public void setSize(String size) {
        this.size = size;
    }
}
    
