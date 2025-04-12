package com.nahuannghia.shopnhn.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nahuannghia.shopnhn.dto.login.LoginRequest;
import com.nahuannghia.shopnhn.dto.login.LoginResponse;
import com.nahuannghia.shopnhn.dto.login.UserResponse;
import com.nahuannghia.shopnhn.model.User;
import com.nahuannghia.shopnhn.repository.UserRepository;
import com.nahuannghia.shopnhn.service.interfaces.IUserService;
import com.nahuannghia.shopnhn.utils.BaseService;
import com.nahuannghia.shopnhn.utils.JwtService;

@Service
public class UserService extends BaseService implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Override
    public LoginResponse authenticate(LoginRequest request) {
        try {
            // Validate input
            if (request.getUsernameOrEmail() == null || request.getPassword() == null) {
                logger.warn("Invalid login request: username or password is null");
                throw new IllegalArgumentException("Username/email and password are required");
            }

            // Find user by username or email
            User user = userRepository.findByUsername(request.getUsernameOrEmail())
                    .orElseGet(() -> userRepository.findByEmail(request.getUsernameOrEmail())
                            .orElseThrow(() -> new UsernameNotFoundException(
                                    "User not found with username or email: " + request.getUsernameOrEmail())));

            // Verify password
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                logger.warn("Invalid password attempt for user: {}", request.getUsernameOrEmail());
                throw new BadCredentialsException("Invalid password");
            }

            // Build user response
            UserResponse userResponse = UserResponse.builder()
                    .userId(user.getUserId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .build();

            // Generate JWT and build login response
            String token = jwtService.generateToken(userResponse.getUserId(),userResponse.getUsername());
            LoginResponse loginResponse = LoginResponse.builder() // Fixed: Use builder() method
                        .token(token)
                        .user(userResponse)
                        .build();

            logger.info("User authenticated successfully: {}", user.getUsername());
            return loginResponse;

        } catch (UsernameNotFoundException | BadCredentialsException e) {
            logger.error("Authentication failed: {}", e.getMessage());
            throw e; // Re-throw specific exceptions for proper handling by controller
        } catch (Exception e) {
            logger.error("Unexpected error during authentication: {}", e.getMessage(), e);
            throw new RuntimeException("Authentication failed due to an unexpected error", e);
        }
    }
}