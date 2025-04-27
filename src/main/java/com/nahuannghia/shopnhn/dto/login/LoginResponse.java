package com.nahuannghia.shopnhn.dto.login;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;


public class LoginResponse {
    private int status;
    private String message;
    private String token;
    private String username;
    private String email;
    private String role;
    private Boolean isActive;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    public LoginResponse(int status, String token, String message, String username, Boolean isActive, LocalDateTime timestamp, String role, String email) {
        this.status = status;
        this.token = token;
        this.message = message;
        this.username = username;
        this.isActive = isActive;
        this.timestamp = timestamp;
        this.role = role;
        this.email = email;
    }
    public LoginResponse() {
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public Boolean getIsActive() {
        return isActive;
    }
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}