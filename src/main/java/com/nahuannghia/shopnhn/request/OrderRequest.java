package com.nahuannghia.shopnhn.request;



import java.math.BigDecimal;
import java.util.List;


public class OrderRequest {
    private Integer userId;
    private Integer paymentMethodId;
    private BigDecimal totalPrice;
    private String note;
    private String phone;
    private String address;
    private List<OrderDetailRequest> listOrderDetail;

    public OrderRequest(Integer userId, Integer paymentMethodId, BigDecimal totalPrice, String note, String phone, String address, List<OrderDetailRequest> listOrderDetail) {
        this.userId = userId;
        this.paymentMethodId = paymentMethodId;
        this.totalPrice = totalPrice;
        this.note = note;
        this.phone = phone;
        this.address = address;
        this.listOrderDetail = listOrderDetail;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OrderDetailRequest> getListOrderDetail() {
        return listOrderDetail;
    }

    public void setListOrderDetail(List<OrderDetailRequest> listOrderDetail) {
        this.listOrderDetail = listOrderDetail;
    }
}
