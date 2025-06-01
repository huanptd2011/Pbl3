package com.nahuannghia.shopnhn.request;

public class ReviewRequest {
    private Integer productId;
    private Integer userId;
    private String reviewContent;
    private Integer rating;

    public ReviewRequest(Integer productId, Integer userId, String reviewContent, Integer rating) {
        this.productId = productId;
        this.userId = userId;
        this.reviewContent = reviewContent;
        this.rating = rating;
    }

    public ReviewRequest() {
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
