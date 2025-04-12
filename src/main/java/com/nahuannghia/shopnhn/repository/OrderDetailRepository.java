package com.nahuannghia.shopnhn.repository;

import com.nahuannghia.shopnhn.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    // Có thể thêm các phương thức tùy chỉnh nếu cần
}

