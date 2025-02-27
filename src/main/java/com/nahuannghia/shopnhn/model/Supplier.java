package com.nahuannghia.shopnhn.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



@Entity
@Table(name="\"Supplier\"")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SupplierID") // Đảm bảo trùng với tên cột trong DB
    private Long supplierID;

    @Column(name = "SupplierName", nullable = false, length = 100)
    private String supplierName;

    @Column(name = "SupplierAddress", length = 200)
    private String supplierAddress;

    @Column(name = "PhoneNumber", length = 15, unique = true)
    private String phoneNumber;

    @Column(name = "Email", length = 100, unique = true)
    private String email;

    // Getters and Setters

    public Supplier(){}
    public Supplier(String supplierName, String supplierAddress, String phoneNumber, String email) {
        this.supplierName = supplierName;
        this.supplierAddress = supplierAddress;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Long getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(Long supplierID) {
        this.supplierID = supplierID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}


