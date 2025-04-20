package com.nahuannghia.shopnhn.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class ProductInventoryId implements Serializable {

    @Column(name = "productId")
    private Integer productId;

    @Column(name = "color", length = 30)
    private String color;

    @Column(name = "size", length = 5)
    private String size;

    // Default constructor
    public ProductInventoryId() {}

    public ProductInventoryId(Integer productId, String color, String size) {
        this.productId = productId;
        this.color = color;
        this.size = size;
    }

    // equals and hashCode should be overridden to ensure correct behavior of composite keys
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductInventoryId that = (ProductInventoryId) o;
        return productId.equals(that.productId) &&
                color.equals(that.color) &&
                size.equals(that.size);
    }

    @Override
    public int hashCode() {
        return 31 * productId.hashCode() + 31 * color.hashCode() + 31 * size.hashCode();
    }
}
