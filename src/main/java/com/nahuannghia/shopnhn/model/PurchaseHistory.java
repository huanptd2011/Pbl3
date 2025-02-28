package com.nahuannghia.shopnhn.model;

import java.time.LocalDateTime;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
@Table(name = "purchase_history")
public class PurchaseHistory {
    @Id
    @Column(name = "history_id")
    private int historyId;
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "Order_id", nullable = false)
    private Order order;
    @Column(name = "purchase_date", nullable = false)
    private LocalDateTime purchaseDate;
    @Column(name = "total_price", precision = 10, nullable = false)  
    private double totalPrice;
    @Column(name = "payment_method", length = 255, nullable = false)
    private String paymentMethod;
    public PurchaseHistory() {
    }
    public PurchaseHistory(int historyId, Customer customer, Order order, LocalDateTime purchaseDate, double totalPrice,
            String paymentMethod) {
        this.historyId = historyId;
        this.customer = customer;
        this.order = order;
        this.purchaseDate = purchaseDate;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
    }
    // Getters
    public int getHistoryId() {
        return historyId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Order getOrder() {
        return order;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    // setters
    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

}
