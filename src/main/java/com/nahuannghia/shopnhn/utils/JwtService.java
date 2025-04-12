package com.nahuannghia.shopnhn.utils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.nahuannghia.shopnhn.config.JwtConfig;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
@Service
public class JwtService {
    private final JwtConfig jwtConfig;
    private final Key key;

    public JwtService(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
        this.key = Keys.hmacShaKeyFor(Base64.getEncoder().encode(jwtConfig.getSecret().getBytes()));
        
    }
    public String generateToken(Long userId,String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtConfig.getExpiration() * 1000); // Thời gian hết hạn
        return Jwts.builder()
                .setSubject(String.valueOf(userId)) // Thay thế bằng thông tin người dùng của bạn
                .setIssuedAt(now) // Thời gian phát hành token
                .setExpiration(expiryDate) // Thời gian hết hạn token
                .claim("email", email) // Thêm thông tin email vào token
                .signWith(SignatureAlgorithm.HS512, key) // Ký token bằng khóa bí mật
                .compact(); // Tạo token thành chuỗi
    }

    
}
