package com.nahuannghia.shopnhn.Response;

import java.math.BigDecimal;

/**
 * Response for sales data over time
 */
public class SalesDataResponse {
    private String timeLabel;
    private BigDecimal revenue;
    
    public SalesDataResponse(String timeLabel, BigDecimal revenue) {
        this.timeLabel = timeLabel;
        this.revenue = revenue;
    }
    
    // Getters and setters
    public String getTimeLabel() {
        return timeLabel;
    }
    
    public void setTimeLabel(String timeLabel) {
        this.timeLabel = timeLabel;
    }
    
    public BigDecimal getRevenue() {
        return revenue;
    }
    
    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }
}