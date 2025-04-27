package com.nahuannghia.shopnhn.request;


public class ProductInventoryRequest {
    private Integer productId;
    private String color;
    private String size;
    private Integer quantity;
    public Integer getProductId() {
        return productId;
    }
    public void setProductId(Integer productId) {
        this.productId = productId;
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
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    // Constructor
    public ProductInventoryRequest(Integer productId, String color, String size, Integer quantity) {
        this.productId = productId;
        this.color = color;
        this.size = size;
        this.quantity = quantity;
    }
    public ProductInventoryRequest() {
    }
}

