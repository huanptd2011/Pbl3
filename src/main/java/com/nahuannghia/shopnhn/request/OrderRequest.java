package com.nahuannghia.shopnhn.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderRequest {
    private Integer userId;
    private Integer paymentMethodId;
    private BigDecimal totalPrice;
    private String note;
}
