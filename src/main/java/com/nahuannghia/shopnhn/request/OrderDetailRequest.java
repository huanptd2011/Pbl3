package com.nahuannghia.shopnhn.request;

import java.math.BigDecimal;

public class OrderDetailRequest {
    private Integer productId;
    private int quantity;
    private String color;
    private String size;
    private BigDecimal price;

    public OrderDetailRequest(Integer productId, int quantity, String color, String size, BigDecimal price) {
        this.productId = productId;
        this.quantity = quantity;
        this.color = color;
        this.size = size;
        this.price = price;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
