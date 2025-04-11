package com.nahuannghia.shopnhn.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInventoryResponse {
    private String color;
    private String size;
    private Integer quantity;
}

