package com.nahuannghia.shopnhn.Response;

public class TopUserSellerResponse {
    private Long userId;
    private String username;
    private String email;
    private Long totalOrders;
    private Double totalSpent;
    private Boolean status;
    //
    public TopUserSellerResponse(Long userId, String username, String email, Long totalOrders, Double totalSpent,
            Boolean status) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.totalOrders = totalOrders;
        this.totalSpent = totalSpent;
        this.status = status;
    }
    public TopUserSellerResponse(){}
    //
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Long getTotalOrders() {
        return totalOrders;
    }
    public void setTotalOrders(Long totalOrders) {
        this.totalOrders = totalOrders;
    }
    public Double getTotalSpent() {
        return totalSpent;
    }
    public void setTotalSpent(Double totalSpent) {
        this.totalSpent = totalSpent;
    }
    public Boolean getStatus() {
        return status;
    }
    public void setStatus(Boolean status) {
        this.status = status;
    }
}