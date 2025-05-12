package com.nahuannghia.shopnhn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nahuannghia.shopnhn.Response.OrderDetailResponse;
import com.nahuannghia.shopnhn.model.OrderDetail;
import com.nahuannghia.shopnhn.model.OrderDetailId;


@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
    List<OrderDetail> findByOrderDetailIdOrderId(Integer orderId);
    @Query("SELECT new com.nahuannghia.shopnhn.Response.OrderDetailResponse(" +
       "od.product.productId, od.quantity, od.total_price, od.color, od.size) " +
       "FROM OrderDetail od WHERE od.order.orderId = :orderId")
List<OrderDetailResponse> findOrderDetailsByOrderId(@Param("orderId") Integer orderId);

}

