package com.nahuannghia.shopnhn.repository;

import com.nahuannghia.shopnhn.model.ProductInventory;
import com.nahuannghia.shopnhn.model.ProductInventoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductInventoryRepository extends JpaRepository<ProductInventory, ProductInventoryId> {
    List<ProductInventory> findByProductProductId(Integer productId);
    void deleteByProductProductId(Integer productId);
    Optional<ProductInventory> findByProductInventoryId_ProductId(Integer productId);
    Optional<ProductInventory> findByProductInventoryId_ProductIdAndProductInventoryId_ColorAndProductInventoryId_Size(
            Integer productId, String color, String size);
    Optional<ProductInventory> findByProductProductIdAndProductInventoryId_SizeAndProductInventoryId_Color(
            Integer productId, String size, String color);
}