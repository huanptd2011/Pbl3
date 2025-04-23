package com.nahuannghia.shopnhn.repository;

import com.nahuannghia.shopnhn.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    boolean existsByPaymentMethod_PaymentMethodId(Integer paymentMethodId);

    @Query("SELECT o FROM Order o WHERE o.orderId LIKE %:keyword%")
    List<Order> searchByOrderId(@Param("keyword") String keyword);

    @Query("SELECT o FROM Order o WHERE o.orderId LIKE %:keyword%")
    Page<Order> searchByOrderId(@Param("keyword") String keyword, Pageable pageable);
    @Modifying
    @Query("DELETE FROM Order o WHERE o.orderId = :orderId")
    int deleteByOrderId(@Param("orderId") String orderId);

    // Use this for SQL Server pagination with OFFSET and FETCH
    @Query(value = "SELECT o FROM Order o WHERE o.orderId LIKE %:keyword%", 
           countQuery = "SELECT count(o) FROM Order o WHERE o.orderId LIKE %:keyword%")
    Page<Order> searchByOrderIdWithPagination(@Param("keyword") String keyword, Pageable pageable);
}
