package com.nahuannghia.shopnhn.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nahuannghia.shopnhn.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> findByEmail(String usernameOrEmail);

    Optional<User> findByUsername(String usernameOrEmail);
    Optional<User>  findByPhone(String phone);
}


