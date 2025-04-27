package com.nahuannghia.shopnhn.dto.update_account_info;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UpdateUserRequest {
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @Size(max = 100, message = "Full name must be less than 100 characters")
    private String fullName;

    @Pattern(regexp = "^\\+?[0-9\\s-]{10,}$", message = "Invalid phone number")
    private String phone;
    
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 6 characters")
    private String password;

    private String address;
    private String dob; // Date of Birth in "yyyy-MM-dd" format
    private String gender;
    private String avatarUrl; // URL of the avatar image
    private String isActive; // Account status (e.g., "ACTIVE", "INACTIVE")
    //
      public UpdateUserRequest() {
    }
    public UpdateUserRequest(
            @NotBlank(message = "Email cannot be blank") @Email(message = "Email should be valid") String email,
            @Size(max = 100, message = "Full name must be less than 100 characters") String fullName,
            @Pattern(regexp = "^\\+?[0-9\\s-]{10,}$", message = "Invalid phone number") String phone,
            @NotBlank(message = "Password cannot be blank") @Size(min = 8, message = "Password must be at least 6 characters") String password,
            String address, String dob, String gender, String avatarUrl, String isActive) {
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.password = password;
        this.address = address;
        this.dob = dob;
        this.gender = gender;
        this.avatarUrl = avatarUrl;
        this.isActive = isActive;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getDob() {
        return dob;
    }
    public void setDob(String dob) {
        this.dob = dob;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getAvatarUrl() {
        return avatarUrl;
    }
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
    public String getIsActive() {
        return isActive;
    }
    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }



}
