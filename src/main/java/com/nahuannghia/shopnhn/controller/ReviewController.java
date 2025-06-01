package com.nahuannghia.shopnhn.controller;

import com.nahuannghia.shopnhn.model.Review;
import com.nahuannghia.shopnhn.request.ReviewRequest;
import com.nahuannghia.shopnhn.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @GetMapping("/{productId}")
    public List<Review> getAllReviewByProductId(@PathVariable("productId") Integer productId){
        return reviewService.getAllReviewByProductId(productId);
    }

    @PostMapping
    public Review postReview(@RequestBody ReviewRequest request){
        return reviewService.postReview(request);
    }
}
