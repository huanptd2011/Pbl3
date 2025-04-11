package com.nahuannghia.shopnhn.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentMethodResponse {
    private Integer paymentMethodId;
    private String paymentMethodName;
}
