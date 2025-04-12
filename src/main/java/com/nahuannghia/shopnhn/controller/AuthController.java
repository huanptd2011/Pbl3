package com.nahuannghia.shopnhn.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nahuannghia.shopnhn.dto.login.LoginRequest;
import com.nahuannghia.shopnhn.dto.login.LoginResponse;
import com.nahuannghia.shopnhn.service.interfaces.IUserService;

import jakarta.validation.Valid;


@Validated
@RestController
@RequestMapping("/api/v1/auth")

public class AuthController {
    private final IUserService userService;
    public AuthController(IUserService userService) {
        this.userService = userService;
    }
     @PostMapping("/login")
  
     public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = userService.authenticate(request);
        return ResponseEntity.ok(response);
     }
}
