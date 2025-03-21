package com.nahuannghia.shopnhn.repository;

import com.nahuannghia.shopnhn.model.ProductCategoryMapping;
import com.nahuannghia.shopnhn.model.ProductCategoryMappingKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryMappingRepository extends JpaRepository<ProductCategoryMapping, ProductCategoryMappingKey> {
}
