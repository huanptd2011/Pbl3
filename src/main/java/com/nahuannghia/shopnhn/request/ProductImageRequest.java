package com.nahuannghia.shopnhn.request;


public class ProductImageRequest {
     private Integer productId;
    private String imageUrl;
    public Integer getProductId() {
        return productId;
    }
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ProductImageRequest(Integer productId, String imageUrl) {
        this.productId = productId;
        this.imageUrl = imageUrl;
    }
    public ProductImageRequest() {
    }
}

