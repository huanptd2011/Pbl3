package com.nahuannghia.shopnhn.model;

import jakarta.persistence.*;



@Entity
@Table(name = "product_category_mapping")

public class ProductCategoryMapping {

    @EmbeddedId
    private ProductCategoryMappingKey id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "Product_id", nullable = false)
    private Product product;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "category_id", nullable = false)
    private ProductCategory productCategory;
    public ProductCategoryMapping() {
    }
    public ProductCategoryMapping(ProductCategoryMappingKey id, Product product, ProductCategory productCategory) {
        this.id = id;
        this.product = product;
        this.productCategory = productCategory;
    }
    //getters
    public ProductCategoryMappingKey getId() {
        return id;
    }
    public Product getProduct() {
        return product;
    }
    public ProductCategory getProductCategory() {
        return productCategory;
    }
    //setters
    public void setId(ProductCategoryMappingKey id) {
        this.id = id;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }
    
}
