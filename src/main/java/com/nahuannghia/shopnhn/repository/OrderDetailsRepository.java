package com.nahuannghia.shopnhn.repository;

import com.nahuannghia.shopnhn.model.OrderDetails;
import com.nahuannghia.shopnhn.model.OrderDetailsKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, OrderDetailsKey> {
}
