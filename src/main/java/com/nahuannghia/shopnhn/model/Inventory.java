package com.nahuannghia.shopnhn.model;
import java.time.LocalDateTime;
<<<<<<< HEAD
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
@Entity
@Table(name = "inventory")
public class Inventory {
    @Id 
=======

import jakarta.persistence.*;

@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    private Long inventoryId;
    @MapsId
>>>>>>> 9d98321 (push code)
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;
    @Column(name = "location", length = 255, nullable = false)
    private String location;
    public Inventory() {
    }
    public Inventory(Product product, int quantity, LocalDateTime lastUpdate, String location) {
        this.product = product;
        this.quantity = quantity;
        this.lastUpdate = lastUpdate;
        this.location = location;
    }
    // Getters
<<<<<<< HEAD
=======

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

>>>>>>> 9d98321 (push code)
    public Product getProduct() {
        return product;
    }
    public int getQuantity() {
        return quantity;
    }
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }
    public String getLocation() {
        return location;
    }
    // Setters
    public void setProduct(Product product) {
        this.product = product;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    public void setLocation(String location) {
        this.location = location;
    }
<<<<<<< HEAD
    


=======
>>>>>>> 9d98321 (push code)
}
