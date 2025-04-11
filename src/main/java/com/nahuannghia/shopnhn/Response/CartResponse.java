package com.nahuannghia.shopnhn.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
    private Integer cartId;
    private Integer customerId;
    private LocalDateTime createdAt;
    private String cartStatus;
}
