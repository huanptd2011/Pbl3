package com.nahuannghia.shopnhn.dto.update_account_info;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    public String getEmail() {
        return email;
    }
    public String getFullName() {
        return fullName;
    }
    public String getPhone() {
        return phone;
    }
    public String getPassword() {
        return password;
    }
    public String getAddress() {
        return address;
    }
    public String getDob() {
        return dob;
    }
    public String getGender() {
        return gender;
    }
    public String getAvatarUrl() {
        return avatarUrl;
    }
    public String getIsActive() {
        return isActive;
    }

    //

    
}
