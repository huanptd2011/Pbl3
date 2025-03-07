package com.nahuannghia.shopnhn.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PromotionProductKey implements Serializable {
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

    // Getters
    public String getProductId() {
        return productId;
    }

    public String getPromotionId() {
        return promotionId;
    }

    // Setters
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    // Override equals() để so sánh hai đối tượng
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PromotionProductKey that = (PromotionProductKey) o;
        return Objects.equals(productId, that.productId) &&
               Objects.equals(promotionId, that.promotionId);
    }

    // Override hashCode() để sinh mã băm từ productId và promotionId
    @Override
    public int hashCode() {
        return Objects.hash(productId, promotionId);
    }
}
