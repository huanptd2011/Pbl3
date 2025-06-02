package com.nahuannghia.shopnhn.Response;

public class OrderPaymentStateResponse {
    private Integer orderId;
    private String paymentState;

    public OrderPaymentStateResponse(Integer orderId, String paymentState) {
        this.orderId = orderId;
        this.paymentState = paymentState;

    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(String paymentState) {
        this.paymentState = paymentState;
    }

}