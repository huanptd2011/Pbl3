package com.nahuannghia.shopnhn.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nahuannghia.shopnhn.model.AdminAccount;
import com.nahuannghia.shopnhn.repository.AdminAccountRepository;

@Service
public class AdminAccountService {
    @Autowired
    private AdminAccountRepository adminAccountRepository;
    
    public List<AdminAccount> getAllAdminAccounts(){
        return adminAccountRepository.findAll();
    }
    public Optional<AdminAccount> getAdminAccountById(Long id){
        return adminAccountRepository.findById(id);
    }
    public AdminAccount createAdminAccount(AdminAccount adminAccount) {
        return adminAccountRepository.save(adminAccount);
    }

    public AdminAccount updateAdminAccount(Long id, AdminAccount updatedAdminAccount) {
        return adminAccountRepository.findById(id).map(adminAccount -> {
            adminAccount.setAdminName(updatedAdminAccount.getAdminName());
            adminAccount.setAdminPasswordHash(updatedAdminAccount.getAdminPasswordHash());
            adminAccount.setAdminEmail(updatedAdminAccount.getAdminEmail());
            adminAccount.setAdminPhone(updatedAdminAccount.getAdminPhone());
            adminAccount.setAdminAddress(updatedAdminAccount.getAdminAddress());
            adminAccount.setAdminCreatedDate(updatedAdminAccount.getAdminCreatedDate());
            return adminAccountRepository.save(adminAccount);
        }).orElse(null);
    }

    public boolean deleteAdminAccount(Long id) {
        if (adminAccountRepository.existsById(id)) {
            adminAccountRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

