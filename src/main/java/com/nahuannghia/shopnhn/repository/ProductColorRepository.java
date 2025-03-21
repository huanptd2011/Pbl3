package com.nahuannghia.shopnhn.repository;

import com.nahuannghia.shopnhn.model.ProductColor;
import com.nahuannghia.shopnhn.model.ProductColorKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductColorRepository extends JpaRepository<ProductColor, ProductColorKey> {
}
