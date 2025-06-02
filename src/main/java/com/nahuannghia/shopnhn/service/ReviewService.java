package com.nahuannghia.shopnhn.service;

import com.nahuannghia.shopnhn.model.Review;
import com.nahuannghia.shopnhn.model.User;
import com.nahuannghia.shopnhn.repository.ReviewRepository;
import com.nahuannghia.shopnhn.repository.UserRepository;
import com.nahuannghia.shopnhn.request.ReviewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;
    public List<Review> getAllReviewByProductId(Integer productId){
        try{
            return reviewRepository.getAllReviewByProductId(productId);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public Review postReview(ReviewRequest request){
        try{
            Review review = new Review();
            review.setProductId(request.getProductId());
            User user = userRepository.findById(request.getUserId()).orElseThrow();
            review.setUser(user);
            review.setReviewContent(request.getReviewContent());
            review.setReviewDate(LocalDateTime.now());
            review.setRating(request.getRating());
            return reviewRepository.save(review);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
