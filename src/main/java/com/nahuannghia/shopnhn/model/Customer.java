package com.nahuannghia.shopnhn.model;

import java.time.LocalDateTime;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @Column(name = "customer_id",length = 30, nullable = false)
    
    private String customerID;
    @Column(name = "customer_name", length = 40, nullable = false)
    private String customerName;
    @Column(name = "customer_email", length = 100, nullable = false)
    private String customerEmail;
    @Column(name = "customer_phone", length = 20, nullable = false)
    private String customerPhone;
    @Column(name = "customer_address", length = 500, nullable = false)
    private String customerAddress;
    @Column(name = "customer_dob",length = 50)
    private LocalDateTime customerDOB;
    @Column(name = "customer", length = 10, nullable = false)
    private String customerGender;

    public Customer() {
    }

<<<<<<< HEAD
    public Customer(String customerID, String customerName, String customerEmail, String customerPhone,
            String customerAddress, LocalDateTime customerDOB, String customerGender) {
=======
    public Customer(String customerID) {
        this.customerID = customerID;
    }

    public Customer(String customerID, String customerName, String customerEmail, String customerPhone,
                    String customerAddress, LocalDateTime customerDOB, String customerGender) {
>>>>>>> 9d98321 (push code)
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.customerAddress = customerAddress;
        this.customerDOB = customerDOB;
        this.customerGender = customerGender;
    }

    // Getters
    public String getCustomerID() {
        return customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public LocalDateTime getCustomerDOB() {
        return customerDOB;
    }

    public String getCustomerGender() {
        return customerGender;
    }

    // Setters
    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public void setCustomerDOB(LocalDateTime customerDOB) {
        this.customerDOB = customerDOB;
    }

    public void setCustomerGender(String customerGender) {
        this.customerGender = customerGender;
    }

}
