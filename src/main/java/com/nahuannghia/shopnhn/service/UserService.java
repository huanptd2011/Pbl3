package com.nahuannghia.shopnhn.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nahuannghia.shopnhn.dto.change_password.ChangePasswordRequest;
import com.nahuannghia.shopnhn.dto.change_password.ChangePasswordResponse;
import com.nahuannghia.shopnhn.dto.create.RegisterRequest;
import com.nahuannghia.shopnhn.dto.create.RegisterResponse;
import com.nahuannghia.shopnhn.dto.delete.DeleteUserResponse;
import com.nahuannghia.shopnhn.dto.login.LoginRequest;
import com.nahuannghia.shopnhn.dto.login.LoginResponse;
import com.nahuannghia.shopnhn.dto.logout.LogoutResponse;
import com.nahuannghia.shopnhn.dto.update_account_info.UpdateUserRequest;
import com.nahuannghia.shopnhn.dto.update_account_info.UpdateUserResponse;
import com.nahuannghia.shopnhn.dto.upload_avatar.UploadAvatarResponse;
import com.nahuannghia.shopnhn.dto.user_info.UserInfoResponse;
import com.nahuannghia.shopnhn.enums.UserRole;
import com.nahuannghia.shopnhn.exeption.UserNotFoundException;
import com.nahuannghia.shopnhn.model.User;
import com.nahuannghia.shopnhn.repository.UserRepository;
import com.nahuannghia.shopnhn.utils.JwtTokenUtil;

import io.micrometer.common.util.StringUtils;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    public UserService(UserRepository userRepository,
                                PasswordEncoder passwordEncoder,
                                JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public UserInfoResponse getUserInfo(Long UserId) throws UserNotFoundException {
        return null;
    }

    public UserInfoResponse getUserInfo(Integer userId)  throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        return UserInfoResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .build();
    }
    public ChangePasswordResponse changePassword(Integer userId, ChangePasswordRequest request)
    {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            return ChangePasswordResponse.builder()
                    .status(400)
                    .message("Current password is incorrect")
                    .timestamp(LocalDateTime.now())
                    .build();
        }

        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            return ChangePasswordResponse.builder()
                    .status(400)
                    .message("New password and confirm password do not match")
                    .timestamp(LocalDateTime.now())
                    .build();
        }

//        User updateduser = user.builder()
//                .password(passwordEncoder.encode(request.getNewPassword()))
//                .build();

        User updateduser = new User(
                user.getUserId(),
                user.getUsername(),
                passwordEncoder.encode(request.getNewPassword()),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getRole(),
                user.getLastLogin(),
                user.getFullName()
        );


        userRepository.save(updateduser);

        return ChangePasswordResponse.builder()
                .status(200)
                .message("Password changed successfully")
                .username(user.getUsername())
                .newPassword(user.getPassword())
                .timestamp(LocalDateTime.now())
                .build();
    }

    public RegisterResponse createUser(RegisterRequest registerRequest)  {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return RegisterResponse.builder()
                    .status(409)
                    .message("Username already exists")
                    .timestamp(LocalDateTime.now())
                    .build();
        }
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return RegisterResponse.builder()
                    .status(409)
                    .message("Email already exists")
                    .timestamp(LocalDateTime.now())
                    .build();
        }
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            return RegisterResponse.builder()
                    .status(400)
                    .message("Password and confirm password do not match")
                    .timestamp(LocalDateTime.now())
                    .build();
        }

        User newUser = User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .role(UserRole.CUSTOMER)
                .createdDate(LocalDateTime.now())
                .status(true)
                .updatedDate(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(newUser);

        return RegisterResponse.builder()
                .status(201)
                .userId(savedUser.getUserId())
                .message("user created successfully")
                .timestamp(LocalDateTime.now())
                .build();
    }

    public DeleteUserResponse deleteUser(Integer userId) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        userRepository.delete(user);

        return DeleteUserResponse.builder()
                .status(200)
                .message("user deleted successfully")
                .timestamp(LocalDateTime.now())
                .build();
    }

    public LoginResponse login(LoginRequest loginRequest) {
        try {
            // Validate input
            if (StringUtils.isBlank(loginRequest.getUsernameOrEmail()) ||
                    StringUtils.isBlank(loginRequest.getPassword())) {
                return buildErrorResponse(400, "Username/Email and password are required");
            }

            // Find user by email or username
            User user = findUserByUsernameOrEmail(loginRequest.getUsernameOrEmail())
                    .orElseThrow(() -> new UserNotFoundException("Account not found"));


            if (user.getPassword() == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                return buildErrorResponse(401, "Invalid username or password");
            }

            // Generate token
            String token = jwtTokenUtil.generateToken(user);

            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);

            return LoginResponse.builder()
                    .status(200)
                    .token(token)
                    .message("Login successful")
                    .username(user.getUsername())
                    .isActive(user.getStatus())
                    .timestamp(LocalDateTime.now())
                    .role(user.getRole().toString())
                    .email(user.getEmail())
                    .build();

        } catch (UserNotFoundException e) {
            return buildErrorResponse(404, "Account not found");
        } catch (Exception e) {
//            log.error("Unexpected error during login", e);
            return buildErrorResponse(500, "Internal server error");
        }
    }

    private Optional<User> findUserByUsernameOrEmail(String usernameOrEmail) {
        // Try both email and username
        Optional<User> userByEmail = userRepository.findByEmail(usernameOrEmail);
        if (userByEmail.isPresent()) {
            return userByEmail;
        }
        return userRepository.findByUsername(usernameOrEmail);
    }

    private LoginResponse buildErrorResponse(int status, String message) {
        return LoginResponse.builder()
                .status(status)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
    public LogoutResponse logout(String token) {
        // Invalidate the token (implementation depends on your token management strategy)
        return LogoutResponse.builder()
                .status(200)
                .message("Logout successful")
                .timestamp(LocalDateTime.now())
                .build();
    }

    public UpdateUserResponse updateUserInfo(Integer userId, UpdateUserRequest userInfo)
            throws UserNotFoundException {

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        User.UserBuilder userBuilder = User.builder();

        if (userInfo.getPassword() != null && !userInfo.getPassword().isEmpty()) {
            String hashedPassword = passwordEncoder.encode(userInfo.getPassword());
            userBuilder.password(hashedPassword);
        }
        if (userInfo.getEmail() != null) {
            userBuilder.email(userInfo.getEmail());
        }


        User updatedUser = userBuilder.build();
        userRepository.save(updatedUser);

        UserInfoResponse userInfoResponse = UserInfoResponse.builder()
                .username(updatedUser.getUsername())
                .email(updatedUser.getEmail())
                .role(updatedUser.getRole().toString())
                .build();

        return UpdateUserResponse.builder()
                .status(200)
                .updatedUser(userInfoResponse)
                .message("user updated successfully")
                .timestamp(LocalDateTime.now())
                .build();
    }
    //    @Override
//    public UploadAvatarResponse uploadAvatar(Integer UserId, MultipartFile file) throws UserNotFoundException {
//        return null;
//    }
//
//    //    @Override
//    public UploadAvatarResponse uploadAvatar(Integer UserId, MultipartFile file) throws UserNotFoundException {
//        return null;
//    }
//
//    //    @Override
//    public UploadAvatarResponse uploadAvatar(Integer userId, MultipartFile file)
//            throws UserNotFoundException {
//        User user = userRepository.findById(userId)
//                .orElseThrow(UserNotFoundException::new);
//
//        return UploadAvatarResponse.builder()
//                .status(200)
//                .message("Avatar uploaded successfully")
//                .timestamp(LocalDateTime.now())
//                .build();
//    }
}