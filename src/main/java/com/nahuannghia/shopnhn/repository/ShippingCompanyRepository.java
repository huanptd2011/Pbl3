package com.nahuannghia.shopnhn.repository;

import com.nahuannghia.shopnhn.model.ShipingCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingCompanyRepository extends JpaRepository<ShipingCompany, String> {
}
