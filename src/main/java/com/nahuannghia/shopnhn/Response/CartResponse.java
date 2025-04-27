package com.nahuannghia.shopnhn.Response;


import java.time.LocalDateTime;
import java.util.List;



public class CartResponse {
    private Integer cartId;
    private Integer userId;
    private LocalDateTime createdAt;
    private String cartStatus;
    List<CartItemResponse> listCartItem;
    //
    public Integer getCartId() {
        return cartId;
    }
    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public String getCartStatus() {
        return cartStatus;
    }
    public void setCartStatus(String cartStatus) {
        this.cartStatus = cartStatus;
    }
    public List<CartItemResponse> getListCartItem() {
        return listCartItem;
    }
    public void setListCartItem(List<CartItemResponse> listCartItem) {
        this.listCartItem = listCartItem;
    }
    public CartResponse(Integer cartId, Integer userId, LocalDateTime createdAt, String cartStatus, List<CartItemResponse> listCartItem) {
        this.cartId = cartId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.cartStatus = cartStatus;
        this.listCartItem = listCartItem;
    }
    public CartResponse() {
    }
}
