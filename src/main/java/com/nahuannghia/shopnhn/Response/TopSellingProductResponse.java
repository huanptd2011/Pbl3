package com.nahuannghia.shopnhn.Response;

import java.math.BigDecimal;

/**
 * Response for top selling products
 */
public class TopSellingProductResponse {
    private Integer productId;
    private String productName;
    private long totalQuantity;
    private BigDecimal totalRevenue;
    
    public TopSellingProductResponse(Integer productId, String productName, long totalQuantity, BigDecimal totalRevenue) {
        this.productId = productId;
        this.productName = productName;
        this.totalQuantity = totalQuantity;
        this.totalRevenue = totalRevenue;
    }
    
    // Getters and setters
    public Integer getProductId() {
        return productId;
    }
    
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public long getTotalQuantity() {
        return totalQuantity;
    }
    
    public void setTotalQuantity(long totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
    
    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }
    
    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}