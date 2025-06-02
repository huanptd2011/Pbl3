package com.nahuannghia.shopnhn.request;

public class OrderPaymentStateRequest {
    private Integer orderId;
    private String paymentState;



    public OrderPaymentStateRequest(Integer orderId, String paymentState) {
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
