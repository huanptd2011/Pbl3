package com.nahuannghia.shopnhn.request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public class CartRequest {
    private Integer customerId;
    private String cartStatus;
    //
    public Integer getCustomerId() {
        return customerId;
    }
    public String getCartStatus() {
        return cartStatus;
    }
}