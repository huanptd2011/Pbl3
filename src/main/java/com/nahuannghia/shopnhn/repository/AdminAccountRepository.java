package com.nahuannghia.shopnhn.repository;

import com.nahuannghia.shopnhn.model.AdminAccount;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AdminAccountRepository extends JpaRepository<AdminAccount, Long> {
    
}
