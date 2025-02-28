package com.nahuannghia.shopnhn.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
@Entity
@Table(name = "product_color")
public class ProductColor {
    @EmbeddedId
    private ProductColorKey id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;
    @Column(name = "color", length = 30, nullable = false)
    private String color;
    @Column(name = "image", length = 500, nullable = false)
    private String image;
    public ProductColor() {
    }
    public ProductColor(ProductColorKey id, Product product, String color, String image) {
        this.id = id;
        this.product = product;
        this.color = color;
        this.image = image;
    }
    //getters
    public ProductColorKey getId() {
        return id;
    }
    public Product getProduct() {
        return product;
    }   
    public String getColor() {
        return color;
    }
    public String getImage() {
        return image;
    }
    //setters
    public void setId(ProductColorKey id) {
        this.id = id;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public void setImage(String image) {
        this.image = image;
    }

    
}
