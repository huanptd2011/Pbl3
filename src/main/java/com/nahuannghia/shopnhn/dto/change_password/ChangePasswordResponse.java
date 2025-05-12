package com.nahuannghia.shopnhn.dto.change_password;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ChangePasswordResponse {
    private String username;
    private int status;
    private String message;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    public ChangePasswordResponse() {
    }
    public ChangePasswordResponse(int status, String message, String username, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.username = username;
        this.timestamp = timestamp;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
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
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
