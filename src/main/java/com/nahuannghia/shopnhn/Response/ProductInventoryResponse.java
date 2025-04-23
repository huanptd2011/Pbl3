package com.nahuannghia.shopnhn.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.NoArgsConstructor;

@Builder

public class ProductInventoryResponse {

    private String color;
    private String size;
    private Integer quantity;
    //get
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
    //constructor
    public ProductInventoryResponse(String color, String size, Integer quantity) {
        this.color = color;
        this.size = size;
        this.quantity = quantity;
    }
    public ProductInventoryResponse() {
    }
}

