package com.nahuannghia.shopnhn.request;

import lombok.Builder;


import java.math.BigDecimal;
@Builder
public class OrderRequest {
    private Integer customerId;
    private Integer paymentMethodId;
    private BigDecimal totalPrice;
    private String note;
    //get
    public Integer getCustomerId() {
        return customerId;
    }
    public Integer getPaymentMethodId() {
        return paymentMethodId;
    }
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    public String getNote() {
        return note;
    }
}
