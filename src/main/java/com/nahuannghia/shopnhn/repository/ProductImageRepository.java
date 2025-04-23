package com.nahuannghia.shopnhn.repository;

import com.nahuannghia.shopnhn.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProductProductId(Integer productId);
    void deleteByProductProductId(Integer productId);
    Optional<ProductImage> findByProductProductIdAndImageUrl(Integer productId, String imageUrl);
}
