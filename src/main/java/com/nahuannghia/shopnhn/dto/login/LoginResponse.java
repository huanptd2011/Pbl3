package com.nahuannghia.shopnhn.dto.login;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
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
}