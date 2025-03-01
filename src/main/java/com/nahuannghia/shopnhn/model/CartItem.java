package com.nahuannghia.shopnhn.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long cartItemId;
    @ManyToOne
    @JoinColumn(name = "cart_id",nullable = false)
    private Cart cart;
    @ManyToOne
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;
    @Column(name = "quantity",nullable = false)
    private int quantity;
    @Column(name = "price",nullable = false)
    private double price;
    public CartItem() {
    }
    public CartItem(Long cartItemId, Cart cart, Product product, int quantity, double price) {
        this.cartItemId = cartItemId;
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }
    //getters
    public Long getCartItemId() {
        return cartItemId;
    }
    public Cart getCart() {
        return cart;
    }
    public Product getProduct() {
        return product;
    }
    public int getQuantity() {
        return quantity;
    }
    public double getPrice() {
        return price;
    }
    //setters
    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }
    public void setCart(Cart cart) {
        this.cart = cart;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    
}
