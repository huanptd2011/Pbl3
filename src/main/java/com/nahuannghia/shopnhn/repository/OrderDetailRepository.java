package com.nahuannghia.shopnhn.repository;

import com.nahuannghia.shopnhn.Response.OrderDetailResponse;
import com.nahuannghia.shopnhn.model.OrderDetail;
import com.nahuannghia.shopnhn.model.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
    List<OrderDetail> findByOrderDetailIdOrderId(Integer orderId);
}

