package com.nahuannghia.shopnhn.repository;

import com.nahuannghia.shopnhn.model.ProductSize;
import com.nahuannghia.shopnhn.model.ProductSizeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductSizeRepository extends JpaRepository<ProductSize, ProductSizeKey> {
    List<ProductSize> findByProduct_ProductId(String productId);
}
