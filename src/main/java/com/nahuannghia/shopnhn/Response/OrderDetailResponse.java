package com.nahuannghia.shopnhn.Response;



import java.math.BigDecimal;

public class OrderDetailResponse {
    private Integer productId;
    private Integer quantity;
    private BigDecimal total_price;
    private String color;
    private String size;
    public OrderDetailResponse(Integer productId, Integer quantity, BigDecimal totalPrice, String color, String size) {
        this.productId = productId;
        this.quantity = quantity;
        this.total_price = totalPrice;
        this.color = color;
        this.size = size;
    }
    public OrderDetailResponse() {
    }
    public Integer getProductId() {
        return productId;
    }
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public BigDecimal getTotal_price() {
        return total_price;
    }
    public void setTotal_price(BigDecimal total_price) {
        this.total_price = total_price;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }
    //

}
