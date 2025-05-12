package com.nahuannghia.shopnhn.Response;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class OrderResponse {
    private Integer orderId;
    private Integer userId;
    private PaymentMethodResponse paymentMethod;
    private LocalDateTime orderDate;
    private BigDecimal totalPrice;
    private String orderState;
    private String note;
    private List<OrderDetailResponse> orderDetails;
    //
public OrderResponse(Integer orderId, Integer userId, PaymentMethodResponse paymentMethodResponse,
    LocalDateTime orderDate, BigDecimal totalPrice, String orderState, String note) {
    this.orderId = orderId;
    this.userId = userId;
    this.paymentMethod = paymentMethodResponse;
    this.orderDate = orderDate;
    this.totalPrice = totalPrice;
    this.orderState = orderState;
    this.note = note;
    this.orderDetails = new ArrayList<>();
}
    // public OrderResponse(Integer orderId, Integer userId, PaymentMethodResponse paymentMethod, LocalDateTime orderDate,
    //         BigDecimal totalPrice, String orderState, String note) {
    //     this.orderId = orderId;
    //     this.userId = userId;
    //     this.paymentMethod = paymentMethod;
    //     this.orderDate = orderDate;
    //     this.totalPrice = totalPrice;
    //     this.orderState = orderState;
    //     this.note = note;
    // }
    public OrderResponse() {
    }
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public PaymentMethodResponse getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodResponse paymentMethod) {
        this.paymentMethod = paymentMethod;
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

    public List<OrderDetailResponse> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailResponse> orderDetails) {
        this.orderDetails = orderDetails;
    }
    //
}
