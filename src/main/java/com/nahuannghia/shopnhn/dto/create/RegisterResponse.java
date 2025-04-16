package com.nahuannghia.shopnhn.dto.create;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse {
    private String username;
    private String email;
    private String password;
    private int status;
    private String message;
    private Long userId;
    private String role;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;


}
