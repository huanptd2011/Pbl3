package com.nahuannghia.shopnhn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nahuannghia.shopnhn.model.ProductCategory;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    // Có thể thêm các phương thức tùy chỉnh nếu cần
    List<ProductCategory> findByCategoryNameContainingIgnoreCase(String name);
        // Truy vấn lấy category của 1 product
    @Query("SELECT p.productCategory FROM Product p WHERE p.productId = :productId")
ProductCategory findCategoryByProductId(@Param("productId") Integer productId);

}
