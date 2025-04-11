package com.nahuannghia.shopnhn.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {
    private Integer cartItemId;
    private Integer productId;
    private String productName;
    private String imageUrl;
    private Integer quantity;
    private BigDecimal price;
    private String color;
    private String size;
    private BigDecimal total;

    public CartItemResponse(Integer cartItemId, Integer productId, String productName, String imageUrl,
                            Integer quantity, BigDecimal price, String color, String size) {
        this.cartItemId = cartItemId;
        this.productId = productId;
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.price = price;
        this.color = color;
        this.size = size;
        this.total = price.multiply(BigDecimal.valueOf(quantity));
    }
}
