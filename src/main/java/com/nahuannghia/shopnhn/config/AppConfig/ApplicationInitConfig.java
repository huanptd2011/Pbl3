package com.nahuannghia.shopnhn.config.AppConfig;

import com.nahuannghia.shopnhn.enums.UserRole;
import com.nahuannghia.shopnhn.model.User;
import com.nahuannghia.shopnhn.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Slf4j
public class ApplicationInitConfig {
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
            try{
                if(userRepository.findByUsername("admin").isEmpty()){
                    User Admin = new User();
                    Admin.setUsername("admin");
                    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                    Admin.setPassword(passwordEncoder.encode("admin123"));
                    Admin.setRole(UserRole.valueOf(UserRole.ADMIN.toString()));
                    userRepository.save(Admin);
                    log.warn("Admin user has been created");
                }
            } catch (Exception e){
                log.error("error");
            }
        };
    }
}