package com.nahuannghia.shopnhn.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin_promotion_log")
public class AdminPromotionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private int logID;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private AdminAccount adminAccount;

   @ManyToOne
    @MapsId("productId")  // Tham chiếu khóa chính trong @Embeddable
    @JoinColumns({
        @JoinColumn(name = "product_id", referencedColumnName = "productId"),
        @JoinColumn(name = "promotion_id", referencedColumnName = "promotionId")
    })
    private PromotionProduct promotionProduct;

    @Column(name = "log_date",nullable = false)
    private LocalDateTime logDate;
    public AdminPromotionLog() {
    }
    public AdminPromotionLog(AdminAccount adminAccount, PromotionProduct promotionProduct, LocalDateTime logDate) {
        this.adminAccount = adminAccount;
        this.promotionProduct = promotionProduct;
        this.logDate = logDate;
    }
    // Getters
    public int getLogID() {
        return logID;
    }

    public AdminAccount getAdminAccount() {
        return adminAccount;
    }

    public PromotionProduct getPromotionProduct() {
        return promotionProduct;
    }

    public LocalDateTime getLogDate() {
        return logDate;
    }

    // Setters
    public void setLogID(int logID) {
        this.logID = logID;
    }

    public void setAdminAccount(AdminAccount adminAccount) {
        this.adminAccount = adminAccount;
    }

    public void setPromotionProduct(PromotionProduct promotionProduct) {
        this.promotionProduct = promotionProduct;
    }

    public void setLogDate(LocalDateTime logDate) {
        this.logDate = logDate;
    }
}
