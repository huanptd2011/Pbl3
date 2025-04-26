package com.nahuannghia.shopnhn.dto.change_password;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;


@Data
@Builder

public class ChangePasswordResponse {
    private String username;
    private String newPassword;
    private int status;
    private String message;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}
