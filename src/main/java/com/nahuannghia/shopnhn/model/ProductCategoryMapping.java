package com.nahuannghia.shopnhn.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
@Entity
@Table(name = "productCategoryMapping")

public class ProductCategoryMapping {

    @EmbeddedId
    private ProductCategoryMappingId id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "categoryId")
    private ProductCategory category;

    // Composite primary key class, nested
    @Embeddable

    public static class ProductCategoryMappingId implements Serializable {

        @Column(name = "productId")
        private Integer productId;

        @Column(name = "categoryId")
        private Integer categoryId;
    }
    public ProductCategoryMappingId getId() {
        return id;
    }
    public void setId(ProductCategoryMappingId id) {
        this.id = id;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public ProductCategory getCategory() {
        return category;
    }
    public void setCategory(ProductCategory category) {
        this.category = category;
    }
    public ProductCategoryMapping() {
    }
    public ProductCategoryMapping(ProductCategoryMappingId id, Product product, ProductCategory category) {
        this.id = id;
        this.product = product;
        this.category = category;
    }
    public Integer getProductId() {
        return id.productId;
    }
    public Integer getCategoryId() {
        return id.categoryId;
    }  
}
