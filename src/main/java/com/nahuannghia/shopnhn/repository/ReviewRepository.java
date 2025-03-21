package com.nahuannghia.shopnhn.repository;

import com.nahuannghia.shopnhn.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
