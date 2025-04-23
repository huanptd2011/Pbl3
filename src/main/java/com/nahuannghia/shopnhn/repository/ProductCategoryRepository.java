package com.nahuannghia.shopnhn.repository;

import java.util.List;

import com.nahuannghia.shopnhn.model.ProductCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    List<ProductCategory> findByCategoryNameContainingIgnoreCase(String name);
}
