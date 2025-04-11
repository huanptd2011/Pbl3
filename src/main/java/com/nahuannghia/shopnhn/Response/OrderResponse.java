package com.nahuannghia.shopnhn.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderResponse {
    private Integer orderId;
    private Integer customerId;
    private Integer paymentMethodId;
    private LocalDateTime orderDate;
    private BigDecimal totalPrice;
    private String orderState;
    private String note;
}
