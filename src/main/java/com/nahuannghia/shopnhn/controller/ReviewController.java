package com.nahuannghia.shopnhn.controller;

import com.nahuannghia.shopnhn.model.Review;
import com.nahuannghia.shopnhn.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @PostMapping
    public Review addReview(@RequestBody Review review){
        return reviewService.addReview(review);
    }
    @GetMapping
    public List<Review> getAllReviews(){
        return reviewService.getAllReviews();
    }
    @GetMapping("/{reviewId}")
    public Review getReview(@PathVariable int reviewId){
        return reviewService.getReview(reviewId);
    }
    @PutMapping("/{reviewId}")
    public Review updateReview(@PathVariable int reviewId, @RequestBody Review review){
        return reviewService.updateReview(reviewId, review);
    }
    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable int reviewId){
        reviewService.deleteReview(reviewId);
    }
}
