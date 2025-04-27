package com.nahuannghia.shopnhn.request;



import java.math.BigDecimal;


public class OrderRequest {
    private Integer userId;
    private Integer paymentMethodId;
    private BigDecimal totalPrice;
    private String note;
    public OrderRequest(Integer userId, Integer paymentMethodId, BigDecimal totalPrice, String note) {
        this.userId = userId;
        this.paymentMethodId = paymentMethodId;
        this.totalPrice = totalPrice;
        this.note = note;
    }
    public OrderRequest() {
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getPaymentMethodId() {
        return paymentMethodId;
    }
    public void setPaymentMethodId(Integer paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }

    //
}
