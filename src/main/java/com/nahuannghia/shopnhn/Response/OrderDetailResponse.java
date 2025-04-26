package com.nahuannghia.shopnhn.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponse {
    private Integer productId;
    private Integer quantity;
    private BigDecimal total_price;
    private String color;
    private String size;
}
