package com.nahuannghia.shopnhn.model;

import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "paymentMethod")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paymentMethod_id")
    private Integer paymentMethodId;

    @Column(name = "paymentMethod_name", nullable = false, length = 50)
    private String paymentMethodName;

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

    //

}
