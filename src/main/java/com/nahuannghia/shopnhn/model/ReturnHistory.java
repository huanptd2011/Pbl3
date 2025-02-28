package com.nahuannghia.shopnhn.model;

import java.time.LocalDateTime;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
@Table(name = "return_history")
public class ReturnHistory {
    @Id
    @Column( length = 30, nullable = false)
    private String returnId;
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "order_id", referencedColumnName = "order_id"),
        @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    })
    private OrderDetails orderDetails;
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    @Column(name = "return_date", nullable = false)
    private LocalDateTime returnDate;
    @Column( name = "return_reason", length = 2000)
    private String returnReason;
    @Column( name = "return_amount", precision = 10, nullable = false)
    private double returnAmount;
    public ReturnHistory() {
    }
    public ReturnHistory(String returnId, OrderDetails orderDetails, Customer customer, LocalDateTime returnDate,
            String returnReason, double returnAmount) {
        this.returnId = returnId;
        this.orderDetails = orderDetails;
        this.customer = customer;
        this.returnDate = returnDate;
        this.returnReason = returnReason;
        this.returnAmount = returnAmount;
    }
    // Getters
    public String getReturnId() {
        return returnId;
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public String getReturnReason() {
        return returnReason;
    }

    public double getReturnAmount() {
        return returnAmount;
    }

    // setters
    public void setReturnId(String returnId) {
        this.returnId = returnId;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    public void setReturnAmount(double returnAmount) {
        this.returnAmount = returnAmount;
    }

}
