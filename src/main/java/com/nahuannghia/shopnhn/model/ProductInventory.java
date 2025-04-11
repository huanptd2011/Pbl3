package com.nahuannghia.shopnhn.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "productInventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public String getSize(){
        return productInventoryId.getSize();
    }
    public String getColor(){
        return productInventoryId.getColor();
    }
}

