package com.nahuannghia.shopnhn.model;
import java.time.LocalDateTime;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
@Entity
@Table(name = "promotion")
public class Promotion {
    @Id
    @Column(name = "promotion_id", length = 30, nullable = false)
    private String promotionId;
    @Column(name = "promotion_name", length = 255, nullable = false)
    private String promotionName;
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;
    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;
    @Column(name = "discount_rate", precision = 5, nullable = false)
    private double discountRate;
    @Column(name = "description", length = 2000)
    private String description;
    @Column(name = "status", nullable = false)
    private boolean status;
    @Column(name = "promotion_details", length = 2000)
    private String promotionDetails;
    public Promotion() {
    }
    public Promotion(String promotionId, String promotionName, LocalDateTime startDate, LocalDateTime endDate,
            double discountRate, String description, boolean status, String promotionDetails) {
        this.promotionId = promotionId;
        this.promotionName = promotionName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discountRate = discountRate;
        this.description = description;
        this.status = status;
        this.promotionDetails = promotionDetails;
    }
    // Getters  
    public String getPromotionId() {
        return promotionId;
    }
    public String getPromotionName() {
        return promotionName;
    }
    public LocalDateTime getStartDate() {
        return startDate;
    }
    public LocalDateTime getEndDate() {
        return endDate;
    }
    public double getDiscountRate() {
        return discountRate;
    }
    public String getDescription() {
        return description;
    }
    public boolean isStatus() {
        return status;
    }
    public String getPromotionDetails() {
        return promotionDetails;
    }
    // Setters
    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }
    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public void setPromotionDetails(String promotionDetails) {
        this.promotionDetails = promotionDetails;
    }



}
