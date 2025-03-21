package com.nahuannghia.shopnhn.repository;

import com.nahuannghia.shopnhn.model.ImageURLProduct;
import com.nahuannghia.shopnhn.model.ImageURLProductKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageURLProductRepository extends JpaRepository<ImageURLProduct, ImageURLProductKey> {
}
