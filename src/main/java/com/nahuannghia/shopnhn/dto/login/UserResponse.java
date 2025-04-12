package com.nahuannghia.shopnhn.dto.login;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Long userId;
    private String username;
    private String email;
    private String phone;
}
