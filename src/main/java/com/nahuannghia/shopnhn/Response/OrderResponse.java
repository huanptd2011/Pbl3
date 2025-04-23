package com.nahuannghia.shopnhn.Response;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class OrderResponse {

    private String orderId;
    private Integer customerId;
    private Integer paymentMethodId;
    private LocalDateTime orderDate;
    private BigDecimal totalPrice;
    private String orderState;
    private String note;
    public OrderResponse(String orderId, Integer customerId, Integer paymentMethodId, LocalDateTime orderDate, BigDecimal totalPrice, String orderState, String note) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.paymentMethodId = paymentMethodId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.orderState = orderState;
        this.note = note;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public Integer getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
    public Integer getPaymentMethodId() {
        return paymentMethodId;
    }
    public void setPaymentMethodId(Integer paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
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
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
 
     //
}
