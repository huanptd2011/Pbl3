package com.nahuannghia.shopnhn.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class ProductImageId implements Serializable {

    @Column(name = "productId")
    private Integer productId;

    @Column(name = "imageUrl", length = 255, insertable = false, updatable = false)
    private String imageUrl;

    public ProductImageId(Integer productId, String imageUrl) {
        this.productId = productId;
        this.imageUrl = imageUrl;
    }
}
