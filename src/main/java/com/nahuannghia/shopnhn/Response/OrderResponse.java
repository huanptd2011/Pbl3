package com.nahuannghia.shopnhn.Response;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class OrderResponse {
    private Integer orderId;
    private Integer userId;
    private String username;
    private PaymentMethodResponse paymentMethod;
    private LocalDateTime orderDate;
    private BigDecimal totalPrice;
    private String orderState;
    private String paymentState;
    private String note;
    private String phone;
    private String address;
    private List<OrderDetailResponse> orderDetails;

    public OrderResponse() {
    }

    public OrderResponse(Integer orderId, Integer userId,String username, PaymentMethodResponse paymentMethod, LocalDateTime orderDate, BigDecimal totalPrice, String orderState,String paymentState, String note, String phone, String address, List<OrderDetailResponse> orderDetails) {
        this.orderId = orderId;
        this.userId = userId;
        this.username = username;
        this.paymentMethod = paymentMethod;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.orderState = orderState;
        this.paymentState = paymentState;
        this.note = note;
        this.phone = phone;
        this.address = address;
        this.orderDetails = orderDetails;
    }
        public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public OrderResponse(Integer orderId, Integer userId, PaymentMethodResponse paymentMethod,
                         LocalDateTime orderDate, BigDecimal totalPrice, String orderState, String note, String phone, String address) {
        this.orderId = orderId;
        this.userId = userId;
        this.paymentMethod = paymentMethod;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.orderState = orderState;
        this.note = note;
        this.phone = phone;
        this.address = address;
        this.orderDetails = new ArrayList<>();
    }
    public OrderResponse(Integer orderId, Integer userId, PaymentMethodResponse paymentMethod,
                         LocalDateTime orderDate, BigDecimal totalPrice, String orderState, String note) {
        this.orderId = orderId;
        this.userId = userId;
        this.paymentMethod = paymentMethod;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.orderState = orderState;
        this.note = note;
    }
        public String getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(String paymentState) {
        this.paymentState = paymentState;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OrderDetailResponse> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailResponse> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
