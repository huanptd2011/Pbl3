package com.nahuannghia.shopnhn.model;


import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "image_url_product")
public class ImageURLProduct {
    @EmbeddedId
    private ImageURLProductKey id;

    @Column(length = 500)
    private String imageURL;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName="product_id", nullable = false)
    private Product product;
    public ImageURLProduct() {
    }

    public ImageURLProduct(ImageURLProductKey id, String imageURL, Product product) {
        this.id = id;
        this.imageURL = imageURL;
        this.product = product;
        
  
    }

    // Getters
    public ImageURLProductKey getId() {
        return id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public Product getProduct() {
        return product;
    }


    // Setters
    public void setId(ImageURLProductKey id) {
        this.id = id;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
