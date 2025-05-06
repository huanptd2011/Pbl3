package com.nahuannghia.shopnhn.config.AppConfig;

import java.time.LocalDateTime;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nahuannghia.shopnhn.enums.UserRole;
import com.nahuannghia.shopnhn.model.User;
import com.nahuannghia.shopnhn.repository.UserRepository;

@Configuration

public class ApplicationInitConfig {
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
            try{
                if(userRepository.findByUsername("ShoeStore").isEmpty()){
                    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                    User Admin = new User(
                        null,
                        "ShoeStore",
                        passwordEncoder.encode("admin123"),
                        "admin@example.com",
                        "0943468506",
                        "54 - Nguyễn Lương Bằng - Liên Chiểu - Đà Nẵng",
                            UserRole.ADMIN,
                            LocalDateTime.now(),
                            "Shoe Store",
                            null,
                            null
                    );
                    userRepository.save(Admin);
                    // log.warn("Admin user has been created");
                }
            } catch (Exception e){
             //   e.printStackTrace();
                // log.error("error");
            }
        };
    }
}