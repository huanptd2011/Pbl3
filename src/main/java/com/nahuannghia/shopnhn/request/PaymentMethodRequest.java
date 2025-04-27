package com.nahuannghia.shopnhn.request;




public class PaymentMethodRequest {
    private String paymentMethodName;
    public PaymentMethodRequest(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }
    public PaymentMethodRequest() {
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }
}
