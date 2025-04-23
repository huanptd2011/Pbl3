package com.nahuannghia.shopnhn.repository;

import com.nahuannghia.shopnhn.model.OrderDetail;
import com.nahuannghia.shopnhn.model.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
<<<<<<< HEAD
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    // Có thể thêm các phương thức tùy chỉnh nếu cần
=======
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
>>>>>>> a72de685ed457ecb97c852db83f5d59ebfecb8e8
}

