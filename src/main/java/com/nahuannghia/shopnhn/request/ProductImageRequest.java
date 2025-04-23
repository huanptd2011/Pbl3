package com.nahuannghia.shopnhn.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImageRequest {
     private Integer productId;
    private String imageUrl;
    public Integer getProductId() {
        return productId;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    //get
}

