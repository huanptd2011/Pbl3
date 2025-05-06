package com.nahuannghia.shopnhn.repository;


import com.nahuannghia.shopnhn.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByUser_UserId(Integer userId);
    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId AND c.cartStatus = :status")
    Cart findCartByUserIdAndStatus(@Param("userId") Integer userId, @Param("status") String status);
}

