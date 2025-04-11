package com.nahuannghia.shopnhn.repository;

import com.nahuannghia.shopnhn.model.Cart;
import com.nahuannghia.shopnhn.model.CartItem;
import com.nahuannghia.shopnhn.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    Optional<CartItem> findByCartAndProductAndColorAndSize(Cart cart, Product product, String color, String size);
    List<CartItem> findByCartCartId(Integer cartId);
    void deleteByCartCartId(Integer cartId);
}

