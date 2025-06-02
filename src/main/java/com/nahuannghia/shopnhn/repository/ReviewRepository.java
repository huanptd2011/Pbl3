package com.nahuannghia.shopnhn.repository;

import com.nahuannghia.shopnhn.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    // Có thể thêm các phương thức tùy chỉnh nếu cần
    @Query("SELECT rv FROM Review rv WHERE rv.productId = :productId ORDER BY rv.reviewDate DESC" )
    List<Review> getAllReviewByProductId(@Param("productId") Integer productId);
}

