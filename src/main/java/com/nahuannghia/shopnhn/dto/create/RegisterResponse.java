package com.nahuannghia.shopnhn.dto.create;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RegisterResponse {
    private String username;
    private String email;
    private int status;
    private String message;
    private Integer userId;
    private String role;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    public RegisterResponse(){}
    public RegisterResponse(int status, String message, String username, String email, Integer userId, String role, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.username = username;
        this.email = email;
        this.userId = userId;
        this.role = role;
        this.timestamp = timestamp;
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
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }


}
