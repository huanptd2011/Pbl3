package com.nahuannghia.shopnhn.repository;


import com.nahuannghia.shopnhn.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByUser_UserId(Integer userId);
}

