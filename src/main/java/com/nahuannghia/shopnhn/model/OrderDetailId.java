package com.nahuannghia.shopnhn.model;

import jakarta.persistence.Column;

public class OrderDetailId {
    private Integer orderId;
    private Integer productId;
    @Column(name = "color", length = 30)
    private String color;

    @Column(name = "size", length = 5)
    private String size;
    //

    public OrderDetailId(Integer orderId, Integer productId, String color, String size) {
        this.orderId = orderId;
        this.productId = productId;
        this.color = color;
        this.size = size;
    }

    public OrderDetailId() {
    }
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
