package com.nahuannghia.shopnhn.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class OrderDetailsKey implements Serializable {
    @Column(name = "order_id",length = 30, nullable = false)
    private String orderId;  // Khớp với OrderID (VARCHAR 30)
    @Column(name = "product_id",length = 30, nullable = false)
    private String productId; // Khớp với ProductID (VARCHAR 30)
    public OrderDetailsKey() {
    }
    public OrderDetailsKey(String orderId, String productId) {
        this.orderId = orderId;
        this.productId = productId;
    }
    // Getters
    public String getOrderId() {
        return orderId;
    }
    public String getProductId() {
        return productId;
    }
    // Setters
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
}
