package com.nahuannghia.shopnhn.repository;

import com.nahuannghia.shopnhn.model.AdminPromotionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminPromotionLogRepository extends JpaRepository<AdminPromotionLog, Integer> {
}
