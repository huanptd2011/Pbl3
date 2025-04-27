package com.nahuannghia.shopnhn.dto.update_account_info;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nahuannghia.shopnhn.dto.user_info.UserInfoResponse;


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
    //
    public int getStatus() {
        return status;
    }
    public UpdateUserResponse(int status, String message, String username, String email, String password, String phone,
            String fullName, String address, String gender, String dob, String avatarUrl, UserInfoResponse updatedUser,
            LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.fullName = fullName;
        this.address = address;
        this.gender = gender;
        this.dob = dob;
        this.avatarUrl = avatarUrl;
        this.updatedUser = updatedUser;
        this.timestamp = timestamp;
    }
    public UpdateUserResponse() {
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getDob() {
        return dob;
    }
    public void setDob(String dob) {
        this.dob = dob;
    }
    public String getAvatarUrl() {
        return avatarUrl;
    }
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
    public UserInfoResponse getUpdatedUser() {
        return updatedUser;
    }
    public void setUpdatedUser(UserInfoResponse updatedUser) {
        this.updatedUser = updatedUser;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}
