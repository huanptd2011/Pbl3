package com.nahuannghia.shopnhn.repository;

import com.nahuannghia.shopnhn.model.ProductSize;
import com.nahuannghia.shopnhn.model.ProductSizeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSizeRepository extends JpaRepository<ProductSize, ProductSizeKey> {
}
