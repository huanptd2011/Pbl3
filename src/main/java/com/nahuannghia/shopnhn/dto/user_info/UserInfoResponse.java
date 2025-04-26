package com.nahuannghia.shopnhn.dto.user_info;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoResponse {

    private Integer id;
    private String username;
    private String fullName;
    private String gender;
    private String phone;
    private String email;
    private String address;
    private LocalDateTime dob;
    private String avatarUrl;
    private String role;
    private Boolean isActive;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedDate;
}