package com.nahuannghia.shopnhn.controller;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nahuannghia.shopnhn.model.AdminAccount;
import com.nahuannghia.shopnhn.service.AdminAccountService;



@RestController
@RequestMapping("/adminaccount")
public class AdminAccountController {
    @Autowired
    private AdminAccountService adminAccountService;

    @GetMapping
    public List<AdminAccount> getAllAdminAccounts() {
        return adminAccountService.getAllAdminAccounts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminAccount> getAdminAccountById(@PathVariable Long id) {
        Optional<AdminAccount> adminAccount = adminAccountService.getAdminAccountById(id);
        return adminAccount.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public AdminAccount createAdminAccount(@RequestBody AdminAccount adminAccount) {
        return adminAccountService.createAdminAccount(adminAccount);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminAccount> updateAdminAccount(@PathVariable Long id, @RequestBody AdminAccount updatedAdminAccount) {
        AdminAccount updated = adminAccountService.updateAdminAccount(id, updatedAdminAccount);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdminAccount(@PathVariable Long id) {
        boolean deleted = adminAccountService.deleteAdminAccount(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}