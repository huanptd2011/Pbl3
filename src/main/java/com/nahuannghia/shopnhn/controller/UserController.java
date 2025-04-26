package com.nahuannghia.shopnhn.controller;

// UserController.java


import java.security.Principal;

import com.nahuannghia.shopnhn.dto.create.RegisterRequest;
import com.nahuannghia.shopnhn.dto.create.RegisterResponse;
import com.nahuannghia.shopnhn.dto.login.LoginRequest;
import com.nahuannghia.shopnhn.dto.login.LoginResponse;
import com.nahuannghia.shopnhn.dto.logout.LogoutResponse;
import com.nahuannghia.shopnhn.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.nahuannghia.shopnhn.dto.change_password.ChangePasswordRequest;
import com.nahuannghia.shopnhn.dto.change_password.ChangePasswordResponse;
import com.nahuannghia.shopnhn.dto.delete.DeleteUserResponse;
import com.nahuannghia.shopnhn.dto.update_account_info.UpdateUserRequest;
import com.nahuannghia.shopnhn.dto.update_account_info.UpdateUserResponse;
import com.nahuannghia.shopnhn.dto.user_info.UserInfoResponse;
import com.nahuannghia.shopnhn.exeption.UserNotFoundException;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins =  {"http://localhost:63342", "http://127.0.0.1:5501", "http://127.0.0.1:5500"})
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserInfoResponse> getUserInfo(
            @PathVariable("userId") Integer accountId) throws UserNotFoundException {
        return ResponseEntity.ok(userService.getUserInfo(accountId));
    }

    @PostMapping("/log-in")
    public ResponseEntity<LoginResponse> loginSystem(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(userService.login(loginRequest));
    }

    @PostMapping("/log-out")
    public LogoutResponse logout(String token){
        return userService.logout(token);
    }

    @PutMapping("/{userId}/change-password")
    public ResponseEntity<ChangePasswordResponse> changePassword(
            @PathVariable("userId") Integer accountId,
            @RequestBody @Valid ChangePasswordRequest request) throws UserNotFoundException {
        return ResponseEntity.ok(userService.changePassword(accountId, request));
    }

    @PostMapping("/create")
    public RegisterResponse createAccount(@RequestBody RegisterRequest request){
        return userService.createUser(request);
    }
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<DeleteUserResponse> deleteUser(
            @PathVariable("userId") Integer accountId) throws UserNotFoundException {
        return ResponseEntity.ok(userService.deleteUser(accountId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UpdateUserResponse> updateUserInfo(
            @PathVariable Integer accountId,
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