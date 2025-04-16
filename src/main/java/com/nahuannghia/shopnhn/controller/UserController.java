package com.nahuannghia.shopnhn.controller;

// UserController.java


import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nahuannghia.shopnhn.dto.change_password.ChangePasswordRequest;
import com.nahuannghia.shopnhn.dto.change_password.ChangePasswordResponse;
import com.nahuannghia.shopnhn.dto.delete.DeleteUserResponse;
import com.nahuannghia.shopnhn.dto.update_account_info.UpdateUserRequest;
import com.nahuannghia.shopnhn.dto.update_account_info.UpdateUserResponse;
import com.nahuannghia.shopnhn.dto.user_info.UserInfoResponse;
import com.nahuannghia.shopnhn.exeption.UserNotFoundException;
import com.nahuannghia.shopnhn.service.user_service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserInfoResponse> getUserInfo(
            @PathVariable Long accountId) throws UserNotFoundException {
        return ResponseEntity.ok(userService.getUserInfo(accountId));
    }

    @PostMapping("/{userId}/change-password")
    public ResponseEntity<ChangePasswordResponse> changePassword(
            @PathVariable Long accountId,
            @RequestBody @Valid ChangePasswordRequest request) throws UserNotFoundException {
        return ResponseEntity.ok(userService.changePassword(accountId, request));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<DeleteUserResponse> deleteUser(
            @PathVariable Long accountId) throws UserNotFoundException {
        return ResponseEntity.ok(userService.deleteUser(accountId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UpdateUserResponse> updateUserInfo(
            @PathVariable Long accountId,
            @RequestBody @Valid UpdateUserRequest request,
            Principal principal) throws UserNotFoundException {
        
        try {
            UserInfoResponse currentUser = userService.getUserInfo(accountId);
            
            if (!currentUser.getUsername().equals(principal.getName()) && 
                !hasAdminRole(principal)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
    
            UpdateUserResponse response = userService.updateUserInfo(accountId, request);
            return ResponseEntity.ok(response);
            
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    private boolean hasAdminRole(Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
    }
}