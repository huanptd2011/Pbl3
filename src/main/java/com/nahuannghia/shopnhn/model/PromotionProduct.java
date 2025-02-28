package com.nahuannghia.shopnhn.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "promotion_product")
public class PromotionProduct {
    @EmbeddedId
    private PromotionProductKey id;

    @ManyToOne
    @MapsId("promotionId")
    @JoinColumn(name = "PromotionID")
    private Promotion promotion;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "ProductID")
    private Product product;
    @Column(name = "discount_price", nullable = false)
    private BigDecimal discountPrice;
    PromotionProduct() {
    }
    PromotionProduct(Promotion promotion, Product product, BigDecimal discountPrice) {
        this.promotion = promotion;
        this.product = product;
        this.discountPrice = discountPrice;
        this.id = new PromotionProductKey(promotion.getPromotionId(), product.getProductId());
    }
    // getters
    public PromotionProductKey getId() {
        return id;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public Product getProduct() {
        return product;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    // setters
    public void setId(PromotionProductKey id) {
        this.id = id;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

}
