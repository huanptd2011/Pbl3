package com.nahuannghia.shopnhn.repository;

import com.nahuannghia.shopnhn.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
