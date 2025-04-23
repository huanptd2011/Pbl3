package com.nahuannghia.shopnhn.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
public class CartItemRequest {
    private Integer cartId;
    private Integer productId;
    private Integer quantity;
    private BigDecimal price;
    private String color;
    private String size;
    public Integer getCartId() {
        return cartId;
    }
    public Integer getProductId() {
        return productId;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public String getColor() {
        return color;
    }
    public String getSize() {
        return size;
    }

    
}
