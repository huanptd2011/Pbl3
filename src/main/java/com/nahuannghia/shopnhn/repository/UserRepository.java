package com.nahuannghia.shopnhn.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nahuannghia.shopnhn.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> findByEmail(String usernameOrEmail);

    Optional<User> findByUsername(String usernameOrEmail);
    Optional<User>  findByPhone(String phone);
    @Query(value = 
    "SELECT u.userId, u.username, u.email, " +  // Added comma after u.email
    "COUNT(o.orderId) as totalOrders, " +
    "COALESCE(SUM(o.total_price), 0) as totalSpent, " +
    "u.status " +  // Fixed the missing space/comma after u.status
    "FROM users u " +
    "LEFT JOIN orders o ON u.userId = o.userId " +
    "GROUP BY u.userId, u.username, u.email, u.status " +  // Added u.status to GROUP BY
    "ORDER BY totalOrders DESC, totalSpent DESC", 
    nativeQuery = true)



    List<Object[]> findTopUsersBySalesStats();
}


