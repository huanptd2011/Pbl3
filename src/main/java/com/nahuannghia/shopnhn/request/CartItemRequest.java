package com.nahuannghia.shopnhn.request;


import java.math.BigDecimal;


public class CartItemRequest {
    private Integer cartId;
    private Integer productId;
    private Integer quantity;
    private BigDecimal price;
    private String color;
    private String size;
    public CartItemRequest(Integer cartId, Integer productId, Integer quantity, BigDecimal price, String color, String size) {
        this.cartId = cartId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.color = color;
        this.size = size;
    }
    public CartItemRequest() {
    }
    public Integer getCartId() {
        return cartId;
    }
    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }
    public Integer getProductId() {
        return productId;
    }
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
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
