package com.nahuannghia.shopnhn.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable

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
    return Objects.equals(productId, that.productId) &&
           Objects.equals(color, that.color) &&
           Objects.equals(size, that.size);
}

@Override
public int hashCode() {
    return Objects.hash(productId, color, size);
}

    //

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
