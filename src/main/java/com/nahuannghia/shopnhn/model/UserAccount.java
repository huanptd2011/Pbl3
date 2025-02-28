package com.nahuannghia.shopnhn.model;

import java.time.LocalDateTime;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@Table(name = "user_account")
public class UserAccount {
    @Id // Đánh dấu customer_id là Primary Key
    @Column(name = "customer_id", length = 30, nullable = false)
    private String customerId;
    @OneToOne
    @MapsId // Ánh xạ customerId với Customer
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    @Column(name = "username", length = 100, nullable = false)
    private String username;
    @Column(name = "password_hash", length = 100, nullable = false)
    private String passwordHash;
    @Column(name = "account_status", length = 10, nullable = false)
    private String accountStatus;
    @Column(name = "date_created", nullable = false)
    private LocalDateTime dateCreated;
    @Column(name = "date_modified", nullable = false)
    private LocalDateTime dateModified;

    public UserAccount() {
    }

    public UserAccount(String customerId, Customer customer, String username, String passwordHash, String accountStatus,
            LocalDateTime dateCreated, LocalDateTime dateModified) {
        this.customerId = customerId;
        this.customer = customer;
        this.username = username;
        this.passwordHash = passwordHash;
        this.accountStatus = accountStatus;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
    }

    // Getters
    public Customer getCustomer() {
        return customer;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public LocalDateTime getDateModified() {
        return dateModified;
    }

    // Setters
    public void setCustomerID(String customerId) {
        this.customerId = customerId;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDateModified(LocalDateTime dateModified) {
        this.dateModified = dateModified;
    }

}
