package com.nahuannghia.shopnhn.Response;

public class PaymentMethodResponse {
    private Integer paymentMethodId;
    private String paymentMethodName;

    // Constructor to match the required arguments
    public PaymentMethodResponse(Integer paymentMethodId, String paymentMethodName) {
        this.paymentMethodId = paymentMethodId;
        this.paymentMethodName = paymentMethodName;
    }
    // Default constructor
    public PaymentMethodResponse() {
    }

    // Getters and setters
    public Integer getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Integer paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }
}
