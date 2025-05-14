package com.nahuannghia.shopnhn.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nahuannghia.shopnhn.Response.OrderResponse;
import com.nahuannghia.shopnhn.Response.OrderStateCount;
import com.nahuannghia.shopnhn.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT o FROM Order o WHERE o.user.userId = :userId")
            List<Order> getAllOrderByUserId(@Param("userId") Integer userId);

    // Có thể thêm các phương thức tùy chỉnh nếu cần
    boolean existsByPaymentMethod_PaymentMethodId(Integer paymentMethodId);

    @Query("SELECT SUM(o.totalPrice) FROM Order o")
    BigDecimal sumTotalRevenue();

    @Query("SELECT COUNT(o) FROM Order o WHERE o.orderState = :state")
    long countByOrderState(@Param("state") String state);

    @Query("SELECT SUM(o.totalPrice) FROM Order o WHERE o.orderDate >= :date")
    BigDecimal sumTotalRevenueFromDate(@Param("date") LocalDateTime date);

    @Query("SELECT o FROM Order o WHERE o.orderDate BETWEEN :start AND :end")
    List<Order> findOrdersBetweenDates(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT o.orderState AS state, COUNT(o) AS count FROM Order o GROUP BY o.orderState")
    List<OrderStateCount> countByOrderState();

    @Query("SELECT p.id, p.productName, SUM(od.quantity), SUM(od.quantity * p.price) "
            + "FROM OrderDetail od "
            + "JOIN od.product p "
            + "JOIN od.order o "
            + "WHERE o.orderDate BETWEEN :startDate AND :endDate "
            + //    "AND o.state = 'Đã giao' " +
            "GROUP BY p.id, p.productName "
            + "ORDER BY SUM(od.quantity) DESC")
    List<Object[]> findTopSellingProducts(LocalDateTime startDate, LocalDateTime endDate);
    @Query("SELECT o FROM Order o ORDER BY o.orderDate DESC")
    List<Order> findTopByOrderByOrderDateDesc();

@Query("SELECT new com.nahuannghia.shopnhn.Response.OrderResponse(" +
       "o.orderId, o.user.id, " +
       "new com.nahuannghia.shopnhn.Response.PaymentMethodResponse(o.paymentMethod.paymentMethodId, o.paymentMethod.paymentMethodName), " +
       "o.orderDate, o.totalPrice, o.orderState, o.note) " +
       "FROM Order o WHERE o.user.id = :userId")
List<OrderResponse> findOrdersByUserId(@Param("userId") Integer userId);
@Query("SELECT new com.nahuannghia.shopnhn.Response.OrderResponse(" +
       "o.orderId, o.user.id, " +
       "new com.nahuannghia.shopnhn.Response.PaymentMethodResponse(o.paymentMethod.paymentMethodId, o.paymentMethod.paymentMethodName), " +
       "o.orderDate, o.totalPrice, o.orderState, o.note) " +
       "FROM Order o WHERE o.user.id = :userId AND LOWER(o.orderState) = LOWER(:orderState)")
List<OrderResponse> findOrdersByUserIdAndOrderState(@Param("userId") Integer userId, @Param("orderState") String orderState);
}