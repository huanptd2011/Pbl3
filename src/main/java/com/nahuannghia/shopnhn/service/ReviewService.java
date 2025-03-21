package com.nahuannghia.shopnhn.service;

import com.nahuannghia.shopnhn.model.Review;
import com.nahuannghia.shopnhn.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    public Review addReview(Review review){
        try {
            return reviewRepository.save(review);
        } catch (Exception e){
            throw new RuntimeException("Error adding");
        }
    }
    public List<Review> getAllReviews(){
        List<Review> list = reviewRepository.findAll();
        if(list.isEmpty())
            throw new RuntimeException("No Reviews");
        return list;
    }
    public Review getReview(int reviewId){
        return reviewRepository.findById(reviewId).orElseThrow(() ->
                new RuntimeException("Not found"));
    }
    public Review updateReview(int reviewId,Review review){
        Review existing = getReview(reviewId);
        existing.setRating(review.getRating());
        existing.setReviewContent(review.getReviewContent());
        existing.setReviewDate(review.getReviewDate());
        existing.setCustomer(review.getCustomer());
        existing.setProduct(review.getProduct());
        try {
            return reviewRepository.save(existing);
        } catch (Exception e){
            throw new RuntimeException("Error updating");
        }
    }
    public void deleteReview(int reviewId){
        Review existing = getReview(reviewId);
        try {
            reviewRepository.delete(existing);
        } catch (Exception e){
            throw new RuntimeException("Error deleting");
        }
    }
}
