package com.nahuannghia.shopnhn.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.NoArgsConstructor;


@Builder
public class ProductImageResponse {
    private String imageUrl;

    public ProductImageResponse() {
        // Default constructor
    }

    public ProductImageResponse(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}