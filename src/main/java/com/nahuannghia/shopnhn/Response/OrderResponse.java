package com.nahuannghia.shopnhn.Response;

import com.nahuannghia.shopnhn.model.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderResponse {
    private Integer orderId;
    private Integer userId;
    private PaymentMethodResponse paymentMethod;
    private LocalDateTime orderDate;
    private BigDecimal totalPrice;
    private String orderState;
    private String note;
    private List<OrderDetailResponse> orderDetails;
}
