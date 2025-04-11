package com.nahuannghia.shopnhn.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInventoryRequest {
    private Integer productId;
    private String color;
    private String size;
    private Integer quantity;
}

