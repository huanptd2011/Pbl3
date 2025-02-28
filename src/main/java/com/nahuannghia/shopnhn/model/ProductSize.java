package com.nahuannghia.shopnhn.model;

import jakarta.persistence.Table;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
@Entity
@Table(name = "product_size")
public class ProductSize {
    @EmbeddedId
    private ProductSizeKey id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    public ProductSize() {
    }

    public ProductSize(Product product, String size) {
        this.product = product;
        this.id = new ProductSizeKey(product.getProductId(), size);
    }

    // getters
    public ProductSizeKey getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public String getSize() {
        return id.getSize(); // Lấy từ khóa chính
    }

    // setters
    public void setId(ProductSizeKey id) {
        this.id = id;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

