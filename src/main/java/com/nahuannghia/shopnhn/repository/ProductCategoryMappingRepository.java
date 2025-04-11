package com.nahuannghia.shopnhn.repository;

import com.nahuannghia.shopnhn.model.ProductCategoryMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryMappingRepository extends JpaRepository<ProductCategoryMapping, ProductCategoryMapping.ProductCategoryMappingId> {
    // Có thể thêm các phương thức tùy chỉnh nếu cần
}

