package com.nahuannghia.shopnhn.Response;


public class ProductInventoryResponse {
    private String color;
    private String size;
    private Integer quantity;
    private Boolean isActive;
    public ProductInventoryResponse(String color, String size, Integer quantity, Boolean isActive) {
        this.color = color;
        this.size = size;
        this.quantity = quantity;
        this.isActive = isActive;
    }
    public Boolean getIsActive() {
        return isActive;
    }
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    public ProductInventoryResponse() {
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
    //
}

