package com.nahuannghia.shopnhn.repository;

import java.util.List;
import java.util.Optional;

import com.nahuannghia.shopnhn.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nahuannghia.shopnhn.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> findByEmail(String usernameOrEmail);

    Optional<User> findByUsername(String usernameOrEmail);
    Optional<User>  findByPhone(String phone);

    @Query("SELECT u FROM User u WHERE u.role = :role")
    List<User> getAllCustomer(@Param("role") UserRole role);
}


