package com.nahuannghia.shopnhn.repository;


import com.nahuannghia.shopnhn.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}
