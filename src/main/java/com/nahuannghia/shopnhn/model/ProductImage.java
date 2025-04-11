package com.nahuannghia.shopnhn.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "productImage")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage implements Serializable {

    @EmbeddedId
    private ProductImageId productImageId;

    @ManyToOne
    @JoinColumn(name = "productId", insertable = false, updatable = false)
    private Product product;

    public ProductImage(Product product, String imageUrl) {
        this.product = product;
        this.productImageId = new ProductImageId(product.getProductId(), imageUrl);
    }
    public String getImageUrl(){
        return productImageId.getImageUrl();
    }
}

