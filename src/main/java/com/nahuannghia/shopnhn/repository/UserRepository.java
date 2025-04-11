package com.nahuannghia.shopnhn.repository;

import com.nahuannghia.shopnhn.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // Có thể thêm các phương thức tùy chỉnh nếu cần
}


