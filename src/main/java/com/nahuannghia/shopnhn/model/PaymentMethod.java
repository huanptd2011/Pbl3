package com.nahuannghia.shopnhn.model;

import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
@Entity
@Table(name = "payment_method")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_method_id")
    private Long PaymentMethodId;
    @Column(name = "payment_method_name", length = 100, nullable = false)
    private String PaymentMethodName;
    public PaymentMethod() {
    }
    public PaymentMethod(Long PaymentMethodId, String PaymentMethodName) {
        this.PaymentMethodId = PaymentMethodId;
        this.PaymentMethodName = PaymentMethodName;
    }
    //getters
    public Long getPaymentMethodId() {
        return PaymentMethodId;
    }
    public String getPaymentMethodName() {
        return PaymentMethodName;
    }
    //setters
    public void setPaymentMethodId(Long PaymentMethodId) {
        this.PaymentMethodId = PaymentMethodId;
    }
    public void setPaymentMethodName(String PaymentMethodName) {
        this.PaymentMethodName = PaymentMethodName;
    }

}
