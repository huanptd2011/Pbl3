package com.nahuannghia.shopnhn.model;

import java.time.LocalDateTime;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private int reviewId;
    @ManyToOne
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;
    @ManyToOne
    @JoinColumn(name="customer_id",nullable = false)
    private Customer customer;
    @Column(name = "review_content", length = 2000, nullable = false)
    private String reviewContent;
    @Column(name = "review_date", nullable = false)
    private LocalDateTime reviewDate;
    @Column(name = "rating", nullable = false)
    private int rating;

    public Review() {
    }

    public Review(Product product, Customer customer, String reviewContent, LocalDateTime reviewDate, int rating) {
        this.product = product;
        this.customer = customer;
        this.reviewContent = reviewContent;
        this.reviewDate = reviewDate;
        this.rating = rating;
    }

    // Getters
    public int getReviewId() {
        return reviewId;
    }

    public Product getProduct() {
        return product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public int getRating() {
        return rating;
    }

    // Setters
    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

}
