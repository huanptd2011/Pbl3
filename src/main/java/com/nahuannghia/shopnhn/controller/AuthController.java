package com.nahuannghia.shopnhn.controller;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nahuannghia.shopnhn.dto.create.RegisterRequest;
import com.nahuannghia.shopnhn.dto.create.RegisterResponse;
import com.nahuannghia.shopnhn.dto.login.LoginRequest;
import com.nahuannghia.shopnhn.dto.login.LoginResponse;
import com.nahuannghia.shopnhn.dto.logout.LogoutResponse;
import com.nahuannghia.shopnhn.service.user_service.UserService;
import org.springframework.context.annotation.Lazy;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController( @Lazy UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }
    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.logout(token.replace("Bearer ", "")));
    }
}

