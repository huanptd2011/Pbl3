package com.nahuannghia.shopnhn.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "admin_account")
public class AdminAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long adminId;

    @Column(name = "admin_name", length = 100,nullable = false)
    private String adminName;

    @Column(name = "admin_password_hash", length = 100,nullable = false)
    private String adminPasswordHash;

    @Column(name = "admin_email", length = 100, unique = true)
    private String adminEmail;

    @Column(name = "admin_phone", length = 20, unique = true, nullable = false)
    private String adminPhone;

    @Column(name = "admin_address", length = 500)
    private String adminAddress;
    @Column(name = "admin_created_date")
    private LocalDateTime adminCreatedDate; 

    public AdminAccount() {
    }

    public AdminAccount(String adminName, String adminPasswordHash, String adminEmail, String adminPhone,
            String adminAddress,
            LocalDateTime adminCreatedDate) {
        this.adminName = adminName;
        this.adminPasswordHash = adminPasswordHash;
        this.adminEmail = adminEmail;
        this.adminPhone = adminPhone;
        this.adminAddress = adminAddress;
        this.adminCreatedDate = adminCreatedDate;
    }

    // Getters
    public Long getAdminId() {
        return adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public String getAdminPasswordHash() {
        return adminPasswordHash;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public String getAdminPhone() {
        return adminPhone;
    }

    public String getAdminAddress() {
        return adminAddress;
    }

    public LocalDateTime getAdminCreatedDate() {
        return adminCreatedDate;
    }

    // Setters
    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public void setAdminPasswordHash(String adminPasswordHash) {
        this.adminPasswordHash = adminPasswordHash;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public void setAdminPhone(String adminPhone) {
        this.adminPhone = adminPhone;
    }

    public void setAdminAddress(String adminAddress) {
        this.adminAddress = adminAddress;
    }

    public void setAdminCreatedDate(LocalDateTime adminCreatedDate) {
        this.adminCreatedDate = adminCreatedDate;
    }
}
