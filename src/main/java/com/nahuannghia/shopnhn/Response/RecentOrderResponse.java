package com.nahuannghia.shopnhn.Response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Response for recent orders
 */
public class RecentOrderResponse {
    private Integer orderId;
    private String username;
    private LocalDateTime orderDate;
    private BigDecimal totalPrice;
    private String orderState;
    
    public RecentOrderResponse(Integer orderId, String username, LocalDateTime orderDate, 
                          BigDecimal totalPrice, String orderState) {
        this.orderId = orderId;
        this.username = username;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.orderState = orderState;
    }
    
    // Getters and setters
    public Integer getOrderId() {
        return orderId;
    }
    
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public LocalDateTime getOrderDate() {
        return orderDate;
    }
    
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
    
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public String getOrderState() {
        return orderState;
    }
    
    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }
}
