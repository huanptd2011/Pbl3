package com.nahuannghia.shopnhn.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemRequest {
    private Integer cartId;
    private Integer productId;
    private Integer quantity;
    private BigDecimal price;
    private String color;
    private String size;
}
