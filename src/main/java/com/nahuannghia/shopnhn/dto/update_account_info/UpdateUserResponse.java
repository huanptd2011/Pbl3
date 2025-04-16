package com.nahuannghia.shopnhn.dto.update_account_info;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nahuannghia.shopnhn.dto.user_info.UserInfoResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserResponse {
    private int status;
    private String message;
    private String username;
    private String email;
    private String password;
    private String phone;
    private String fullName;
    private String address;
    private String gender;
    private String dob;
    private String avatarUrl;
    private UserInfoResponse updatedUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

}
