package com.nahuannghia.shopnhn.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
import com.nahuannghia.shopnhn.dto.user_info.UserInfoResponse;
import com.nahuannghia.shopnhn.enums.UserRole;
import com.nahuannghia.shopnhn.exeption.AccessDeniedException;
import com.nahuannghia.shopnhn.exeption.UserNotFoundException;
import com.nahuannghia.shopnhn.model.User;
import com.nahuannghia.shopnhn.repository.UserRepository;
import com.nahuannghia.shopnhn.utils.JwtTokenUtil;

import io.micrometer.common.util.StringUtils;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    public UserService(UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public UserInfoResponse getUserInfo(Integer userId) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        return new UserInfoResponse(
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getRole().toString(),
                user.getFullName(),
                user.getDob()
        );
    }

    public List<UserInfoResponse> getAllCustomer(){
        try {
            List<User> list = userRepository.getAllCustomer(UserRole.CUSTOMER);
            return list.stream().map(UserInfoResponse::new).collect(Collectors.toList());
        } catch (Exception e){
            return null;
        }
    }

    public ChangePasswordResponse changePassword(ChangePasswordRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            return new ChangePasswordResponse(400, "Mật khẩu hiện tại không đúng", null, LocalDateTime.now());
        }

        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            return new ChangePasswordResponse(400, "Mật khẩu xác nhận không đúng", null, LocalDateTime.now());
        }

        User updatedUser = new User(
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

        userRepository.save(updatedUser);

        return new ChangePasswordResponse(
                200,
                "Mật khẩu đã thay đổi thành công",
                user.getUsername(),
                LocalDateTime.now()
        );
    }

    public RegisterResponse createUser(RegisterRequest registerRequest) {
//        if (userRepository.existsByUsername(registerRequest.getUsername())) {
//            return new RegisterResponse(409, null, "Username already exists", null, null, null, null, LocalDateTime.now());
//        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return new RegisterResponse(409, null, "Email already exists", null, null, null, LocalDateTime.now());
        }

        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            return new RegisterResponse(400, null, "Password and confirm password do not match", null, null, null, LocalDateTime.now());
        }

        User newUser = new User();
        newUser.setUsername(createUsername(registerRequest.getEmail()));
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setEmail(registerRequest.getEmail());
        newUser.setRole(UserRole.CUSTOMER);
        newUser.setCreatedDate(LocalDateTime.now());
        newUser.setStatus(true);
        newUser.setUpdatedDate(LocalDateTime.now());
        newUser.setFullName(registerRequest.getFirstName() + registerRequest.getLastName());
        newUser.setPhone(registerRequest.getPhone());

        User savedUser = userRepository.save(newUser);

        return new RegisterResponse(
                201,
                "User created successfully",
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getUserId(),
                savedUser.getRole().toString() != null ? savedUser.getRole().toString() : null,
                LocalDateTime.now()
        );

    }

    public DeleteUserResponse deleteUser(String targetUsername) throws UserNotFoundException {
        // Lấy username của người đang đăng nhập từ security context
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        // Tìm người dùng hiện tại (caller)
        User caller = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new UserNotFoundException("Current user not found"));

        // Nếu caller không phải ADMIN và cố gắng xóa người khác
        if (!caller.getRole().equals("ADMIN") && !caller.getUsername().equals(targetUsername)) {
            throw new AccessDeniedException("Không có quyền xóa user này");
        }

        // Tìm người dùng cần xóa
        User targetUser = userRepository.findByUsername(targetUsername)
                .orElseThrow(() -> new UserNotFoundException("Người dùng cần xóa không tồn tại"));

        // Xóa user
        userRepository.delete(targetUser);

        return new DeleteUserResponse(200, "User deleted successfully", LocalDateTime.now());
    }

    public LoginResponse login(LoginRequest loginRequest) {
        try {
            if (StringUtils.isBlank(loginRequest.getUsernameOrEmail())
                    || StringUtils.isBlank(loginRequest.getPassword())) {
                return new LoginResponse(null, 400, null, "Username/Email and password are required", null, false, LocalDateTime.now(),
                        null, null , null, null, null, null, null);
            }

            Optional<User> optionalUser = findUserByUsernameOrEmail(loginRequest.getUsernameOrEmail());
            if (optionalUser.isEmpty() || !passwordEncoder.matches(loginRequest.getPassword(), optionalUser.get().getPassword())) {
                return new LoginResponse(null, 401, null, "Invalid username or password", null, false, LocalDateTime.now(),
                        null, null, null, null, null, null, null);
            }

            User user = optionalUser.get();
            String token = jwtTokenUtil.generateToken(user);

            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);

            return new LoginResponse(
                    user.getUserId(),
                    200,
                    token,
                    "Login successful",
                    user.getUsername(),
                    user.getStatus(),
                    LocalDateTime.now(),
                    user.getRole().toString(),
                    user.getEmail(),
                    user.getFullName(),
                    user.getDob(),
                    user.getPhone(),
                    user.getAddress(),
                    user.getAvatar()
            );

        } catch (Exception e) {
            logger.error("Unexpected error during login", e);
            return new LoginResponse(null, 500, null, "Internal server error", null, false, LocalDateTime.now(),
                    null, null, null, null, null, null, null);
        }
    }

    public LogoutResponse logout(String token) {
        return new LogoutResponse(200, "Logout successful", LocalDateTime.now());
    }

    public UpdateUserResponse updateUserInfo(Integer userId, UpdateUserRequest userInfo)
            throws UserNotFoundException {

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

// ✅ Kiểm tra quyền truy cập (nếu có Spring Security)
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new AccessDeniedException("Not authenticated"));

        if (!currentUser.getRole().equals("ADMIN") && !currentUser.getUserId().equals(userId)) {
            throw new AccessDeniedException("Bạn không có quyền sửa thông tin người dùng này");
        }

// ✅ Cập nhật các trường nếu có
        if (userInfo.getPassword() != null && !userInfo.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        }
        if (userInfo.getEmail() != null) {
            user.setEmail(userInfo.getEmail());
        }
        if (userInfo.getPhone() != null) {
            user.setPhone(userInfo.getPhone());
        }
        if (userInfo.getAddress() != null) {
            user.setAddress(userInfo.getAddress());
        }
        if (userInfo.getFullName() != null) {
            user.setFullName(userInfo.getFullName());
        }

        user.setUpdatedDate(LocalDateTime.now());
        userRepository.save(user);

        UserInfoResponse userInfoResponse = new UserInfoResponse(
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getRole().toString(),
                user.getFullName(),
                user.getDob()
        );

        return new UpdateUserResponse(
                200,
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getRole().toString(),
                user.getFullName(),
                user.getStatus() != null ? user.getStatus().toString() : null,
                user.getCreatedDate() != null ? user.getCreatedDate().toString() : null,
                user.getUpdatedDate() != null ? user.getUpdatedDate().toString() : null,
                user.getLastLogin() != null ? user.getLastLogin().toString() : null,
                userInfoResponse,
                LocalDateTime.now()
        );
    }

    private Optional<User> findUserByUsernameOrEmail(String usernameOrEmail) {
        Optional<User> userByEmail = userRepository.findByEmail(usernameOrEmail);
        return userByEmail.isPresent() ? userByEmail : userRepository.findByUsername(usernameOrEmail);
    }

    private String createUsername(String email) {
        return email.substring(0, email.indexOf("@"));
    }

}
