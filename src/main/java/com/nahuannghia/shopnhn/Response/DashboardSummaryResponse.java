package com.nahuannghia.shopnhn.Response;


import java.math.BigDecimal;

/**
 * Response for dashboard summary metrics
 */
public class DashboardSummaryResponse {
    private BigDecimal totalRevenue;
    private long totalOrders;
    private long totalUsers;
    private long lowStockProducts;
    private long pendingOrders;
    private BigDecimal monthlyRevenue;
    
    public DashboardSummaryResponse(BigDecimal totalRevenue, long totalOrders, long totalUsers, 
                              long lowStockProducts, long pendingOrders, BigDecimal monthlyRevenue) {
        this.totalRevenue = totalRevenue;
        this.totalOrders = totalOrders;
        this.totalUsers = totalUsers;
        this.lowStockProducts = lowStockProducts;
        this.pendingOrders = pendingOrders;
        this.monthlyRevenue = monthlyRevenue;
    }
    
    // Getters and setters
    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }
    
    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
    
    public long getTotalOrders() {
        return totalOrders;
    }
    
    public void setTotalOrders(long totalOrders) {
        this.totalOrders = totalOrders;
    }
    
    public long getTotalUsers() {
        return totalUsers;
    }
    
    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }
    
    public long getLowStockProducts() {
        return lowStockProducts;
    }
    
    public void setLowStockProducts(long lowStockProducts) {
        this.lowStockProducts = lowStockProducts;
    }
    
    public long getPendingOrders() {
        return pendingOrders;
    }
    
    public void setPendingOrders(long pendingOrders) {
        this.pendingOrders = pendingOrders;
    }
    
    public BigDecimal getMonthlyRevenue() {
        return monthlyRevenue;
    }
    
    public void setMonthlyRevenue(BigDecimal monthlyRevenue) {
        this.monthlyRevenue = monthlyRevenue;
    }
}







