package com.nahuannghia.shopnhn.dto.user_info;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;


@Builder
public class UserInfoResponse {

    private Long id;
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
    public Long getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getFullName() {
        return fullName;
    }
    public String getGender() {
        return gender;
    }
    public String getPhone() {
        return phone;
    }
    public String getEmail() {
        return email;
    }
    public String getAddress() {
        return address;
    }
    public LocalDateTime getDob() {
        return dob;
    }
    public String getAvatarUrl() {
        return avatarUrl;
    }
    public String getRole() {
        return role;
    }
    public Boolean getIsActive() {
        return isActive;
    }
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    //
    

}