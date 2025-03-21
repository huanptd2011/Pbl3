package com.nahuannghia.shopnhn.model;
import java.math.BigDecimal;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

import jakarta.persistence.Transient;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
@Entity
public class OrderDetails {

    @EmbeddedId
    private OrderDetailsKey id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id", nullable = false)
    private Order order; 

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column( name = "quantity", nullable = false)
    private int quantity;

    @Column( name = "unit_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    @Column( name = "unit", length = 20, nullable = false)
    private String unit;

    @Column( name = "discount", precision = 5, scale = 2)
    private BigDecimal discount;

    @Transient // Bỏ qua cột computed TotalPrice
    private BigDecimal totalPrice;
<<<<<<< HEAD
=======

    public OrderDetails() {
    }

    public OrderDetails(OrderDetailsKey id, Order order, Product product, int quantity, BigDecimal unitPrice, String unit, BigDecimal discount, BigDecimal totalPrice) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.unit = unit;
        this.discount = discount;
        this.totalPrice = totalPrice;
    }

    public OrderDetailsKey getId() {
        return id;
    }

    public void setId(OrderDetailsKey id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
>>>>>>> 9d98321 (push code)
}

