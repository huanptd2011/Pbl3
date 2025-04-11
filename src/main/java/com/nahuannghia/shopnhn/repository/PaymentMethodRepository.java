package com.nahuannghia.shopnhn.repository;

import com.nahuannghia.shopnhn.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {
    // Có thể thêm các phương thức tùy chỉnh nếu cần
    List<PaymentMethod> findByPaymentMethodNameContainingIgnoreCase(String name);

}

