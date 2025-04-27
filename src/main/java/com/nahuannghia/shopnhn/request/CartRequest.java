package com.nahuannghia.shopnhn.request;


public class CartRequest {
    private Integer userId;
    private String cartStatus;
    public CartRequest(Integer userId, String cartStatus) {
        this.userId = userId;
        this.cartStatus = cartStatus;
    }
    public CartRequest() {
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getCartStatus() {
        return cartStatus;
    }
    public void setCartStatus(String cartStatus) {
        this.cartStatus = cartStatus;
    }
}