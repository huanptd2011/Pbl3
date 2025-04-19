package com.nahuannghia.shopnhn.dto.login;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "Username or email is required")
    @Size(min = 8, max = 255, message = "Username/email must be between 3 and 100 characters")
    private String usernameOrEmail;
    
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    private String password;
    
    //getter
    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }
 
}
