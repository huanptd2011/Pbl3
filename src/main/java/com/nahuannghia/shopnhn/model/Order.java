
package com.nahuannghia.shopnhn.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "[Order]")

public class Order {

    @Id
    @Column( length = 30, nullable = false)
    private String orderId; 

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer; 

    @Column( name = "order_status", length = 50, nullable = false)
    private String orderStatus;

    @Column(name = "total_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalAmount;

    @ManyToOne
    @JoinColumn(name = "payment_method_id", nullable = false)
    private PaymentMethod paymentMethod; // Giả sử có entity PaymentMethod

    @Column( name = "shipping_address", length = 500, nullable = false)
    private String shippingAddress;

    @Column(name = "notes", length = 2000)
    private String notes;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;


    @ManyToOne
    @JoinColumn(name = "shipping_company_id", nullable = false)
    private ShipingCompany shippingCompany; // Giả sử có entity ShippingCompany
<<<<<<< HEAD
=======

    public Order() {
    }

    public Order(String orderId, Customer customer, String orderStatus, BigDecimal totalAmount, PaymentMethod paymentMethod, String shippingAddress, String notes, LocalDateTime orderDate, ShipingCompany shippingCompany) {
        this.orderId = orderId;
        this.customer = customer;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.shippingAddress = shippingAddress;
        this.notes = notes;
        this.orderDate = orderDate;
        this.shippingCompany = shippingCompany;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public ShipingCompany getShippingCompany() {
        return shippingCompany;
    }

    public void setShippingCompany(ShipingCompany shippingCompany) {
        this.shippingCompany = shippingCompany;
    }
>>>>>>> 9d98321 (push code)
}
