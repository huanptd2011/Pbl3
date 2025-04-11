package com.nahuannghia.shopnhn.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageRequest {
    private Integer productId;
    private String imageUrl;
}

