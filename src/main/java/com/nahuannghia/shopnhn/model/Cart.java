package com.nahuannghia.shopnhn.model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;
    @ManyToOne
    @JoinColumn(name = "customer_id",nullable = false)
    private Customer customer;
    @Column(name = "created_date",nullable = false)
    private LocalDateTime createdDate;
    @Column(name = "status",nullable = false)
    private String status;
    public Cart() {
    }
    public Cart(Long cartId, Customer customer, LocalDateTime createdDate, String status) {
        this.cartId = cartId;
        this.customer = customer;
        this.createdDate = createdDate;
        this.status = status;
    }
    //getters
    public Long getCartId() {
        return cartId;
    }
    public Customer getCustomer() {
        return customer;
    }
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    public String getStatus() {
        return status;
    }
    //setters
    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
    public void setStatus(String status) {
        this.status = status;
    }


}
