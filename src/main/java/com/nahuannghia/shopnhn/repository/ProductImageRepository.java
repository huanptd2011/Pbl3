package com.nahuannghia.shopnhn.repository;

import com.nahuannghia.shopnhn.model.ProductImage;
import com.nahuannghia.shopnhn.model.ProductImageId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, ProductImageId> {
    List<ProductImage> findByProductProductId(Integer productId);
    Optional<ProductImage> findByProductImageId_ImageUrl(String imageUrl);
    void deleteByProductProductId(Integer productId);
}
