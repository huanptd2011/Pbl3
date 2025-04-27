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
                if(userRepository.findByUsername("admin").isEmpty()){
                    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                    User Admin = new User(
                        null, // Assuming ID is auto-generated
                        "admin",
                        passwordEncoder.encode("admin123"),
                        "Admin First Name", // Replace with appropriate value
                        "Admin Last Name",  // Replace with appropriate value
                        "admin@example.com", // Replace with appropriate value
                        UserRole.ADMIN,
                        LocalDateTime.now(), // Assuming current time for creation date
                        "Admin Address" // Replace with appropriate value
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