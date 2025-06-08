package com.nahuannghia.shopnhn.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nahuannghia.shopnhn.dto.ResetPasswordRequest;
import com.nahuannghia.shopnhn.model.User;
import com.nahuannghia.shopnhn.repository.UserRepository;
import com.nahuannghia.shopnhn.utils.JwtTokenUtil;

@Service
public class ResetPasswordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtTokenUtil jwtService;

    public boolean sendResetLink(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) return false;

        String token = jwtService.generateResetToken(email);
        String link = "http://localhost:5173/reset-password?token=" + token;
        String content = "Click vào link sau để đặt lại mật khẩu:\n" + link;

        emailService.sendSimpleMail(email, "Đặt lại mật khẩu", content);
        return true;
    }

    public boolean resetPassword(ResetPasswordRequest request) {
    String token = request.getToken();
    String newPassword = request.getPassword();
    String confirmPassword = request.getConfirmPassword();
     System.out.println("Received token: " + token);
    boolean valid = jwtService.isTokenValid(token);
    System.out.println("Is token valid? " + valid);
    if (!valid) return false;

    // 2. Kiểm tra password và confirmPassword có trùng nhau
    if (newPassword == null || !newPassword.equals(confirmPassword)) return false;

    // 3. Lấy email từ token
    String email = jwtService.extractEmailFromToken(token);
    System.out.println("Extracted email: " + email);
    Optional<User> userOpt = userRepository.findByEmail(email);
    if (userOpt.isEmpty()) return false;

    // 4. Cập nhật mật khẩu đã mã hóa
    User user = userOpt.get();
    user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
    userRepository.save(user);

    return true;
}


}
