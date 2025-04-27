package com.nahuannghia.shopnhn.model;


import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "productInventory")


public class ProductInventory {

    @EmbeddedId
    private ProductInventoryId productInventoryId;

    @ManyToOne
    @JoinColumn(name = "productId", insertable = false, updatable = false)
    private Product product;

    @Column(name = "quantity", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int quantity;

    public ProductInventory(Product product, String color, String size, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.productInventoryId = new ProductInventoryId(product.getProductId() , color, size);
    }
    public ProductInventory() {
    }

    public String getSize(){
        return productInventoryId.getSize();
    }
    public String getColor(){
        return productInventoryId.getColor();
    }

    public ProductInventoryId getProductInventoryId() {
        return productInventoryId;
    }

    public void setProductInventoryId(ProductInventoryId productInventoryId) {
        this.productInventoryId = productInventoryId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    //
}

