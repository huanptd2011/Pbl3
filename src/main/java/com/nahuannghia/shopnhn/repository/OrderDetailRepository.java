package com.nahuannghia.shopnhn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nahuannghia.shopnhn.model.OrderDetail;
import com.nahuannghia.shopnhn.model.OrderDetailId;


@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
    List<OrderDetail> findByOrderDetailIdOrderId(Integer orderId);
}

