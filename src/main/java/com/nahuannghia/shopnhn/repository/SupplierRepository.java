package com.nahuannghia.shopnhn.repository;

import org.springframework.stereotype.Repository;
import com.nahuannghia.shopnhn.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    
}
