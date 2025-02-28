package com.nahuannghia.shopnhn.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PromotionProductKey implements java.io.Serializable {
    @Column(name = "product_id", length = 30, nullable = false)
    private String productId;
    @Column(name = "promotion_id", length = 30, nullable = false)
    private String promotionId;

    public PromotionProductKey() {
    }

    public PromotionProductKey(String productId, String promotionId) {
        this.productId = productId;
        this.promotionId = promotionId;
    }

    // getters
    public String getProductId() {
        return productId;
    }

    public String getPromotionId() {
        return promotionId;
    }

    // setters
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

}
