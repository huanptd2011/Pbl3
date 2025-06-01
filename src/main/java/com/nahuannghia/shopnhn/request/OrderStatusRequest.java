package com.nahuannghia.shopnhn.request;

public class OrderStatusRequest {
    private Integer orderId;
    private String orderState;
    private String paymentState;

    public OrderStatusRequest(Integer orderId, String orderState, String paymentState) {
        this.orderId = orderId;
        this.orderState = orderState;
        this.paymentState = paymentState;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(String paymentState) {
        this.paymentState = paymentState;
    }   

}