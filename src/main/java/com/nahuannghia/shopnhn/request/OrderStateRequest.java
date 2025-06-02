package com.nahuannghia.shopnhn.request;

public class OrderStateRequest {
    private Integer orderId;
    private String orderState;


    public OrderStateRequest(Integer orderId, String orderState) {
        this.orderId = orderId;
        this.orderState = orderState;

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
}