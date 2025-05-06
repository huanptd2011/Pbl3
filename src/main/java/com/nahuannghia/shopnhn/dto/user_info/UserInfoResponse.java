package com.nahuannghia.shopnhn.dto.user_info;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nahuannghia.shopnhn.model.User;

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
    public UserInfoResponse(String username, String email, String phone, String address, String role) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = role;
    }
    public UserInfoResponse() {
    }

    public UserInfoResponse(User user) {
        this.id = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole().toString();
        this.fullName = user.getFullName();
        this.isActive = user.getStatus();
        this.createdDate = user.getCreatedDate();
        this.address = user.getAddress();
        this.phone = user.getPhone();
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public LocalDateTime getDob() {
        return dob;
    }
    public void setDob(LocalDateTime dob) {
        this.dob = dob;
    }
    public String getAvatarUrl() {
        return avatarUrl;
    }
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }
    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
}