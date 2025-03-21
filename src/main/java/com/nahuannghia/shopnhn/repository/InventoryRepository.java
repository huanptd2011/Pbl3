package com.nahuannghia.shopnhn.repository;

import com.nahuannghia.shopnhn.model.Inventory;
import com.nahuannghia.shopnhn.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByProduct(Product product);
}
