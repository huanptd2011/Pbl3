package com.nahuannghia.shopnhn.repository;

import com.nahuannghia.shopnhn.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    // Có thể thêm các phương thức tùy chỉnh nếu cần
}

