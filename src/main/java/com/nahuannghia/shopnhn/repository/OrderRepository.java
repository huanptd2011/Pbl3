package com.nahuannghia.shopnhn.repository;

import com.nahuannghia.shopnhn.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    // Có thể thêm các phương thức tùy chỉnh nếu cần
    boolean existsByPaymentMethod_PaymentMethodId(Integer paymentMethodId);
}
