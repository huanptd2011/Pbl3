package com.nahuannghia.shopnhn.repository;

import com.nahuannghia.shopnhn.model.PromotionProduct;
import com.nahuannghia.shopnhn.model.PromotionProductKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionProductRepository extends JpaRepository<PromotionProduct, PromotionProductKey> {
}
