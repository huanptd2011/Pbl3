package com.nahuannghia.shopnhn.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
    private Integer cartId;
    private Integer userId;
    private LocalDateTime createdAt;
    private String cartStatus;
    List<CartItemResponse> listCartItem;
}
