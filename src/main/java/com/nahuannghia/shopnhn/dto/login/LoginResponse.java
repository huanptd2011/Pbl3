package com.nahuannghia.shopnhn.dto.login;

public class LoginResponse {
    private String token;
    private UserResponse user;

    // Private constructor to enforce builder usage
    private LoginResponse() {}

    // Getters
    public String getToken() {
        return token;
    }

    public UserResponse getUser() {
        return user;
    }

    // Builder class
    public static class Builder {
        private String token;
        private UserResponse user;

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder user(UserResponse user) {
            this.user = user;
            return this;
        }

        public LoginResponse build() {
            LoginResponse response = new LoginResponse();
            response.token = this.token;
            response.user = this.user;
            return response;
        }
    }

    // Static method to get a new builder instance
    public static Builder builder() {
        return new Builder();
    }
}