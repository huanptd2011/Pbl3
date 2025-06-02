package com.nahuannghia.shopnhn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nahuannghia.shopnhn.Response.OrderDetailResponse;
import com.nahuannghia.shopnhn.model.OrderDetail;
import com.nahuannghia.shopnhn.model.OrderDetailId;


@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
    @EntityGraph(attributePaths = {"product", "product.images"})
    List<OrderDetail> findByOrder_OrderId(Integer orderId);
}

