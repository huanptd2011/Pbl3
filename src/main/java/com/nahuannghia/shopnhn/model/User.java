package com.nahuannghia.shopnhn.model;

import com.nahuannghia.shopnhn.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Integer userId;

    @Column(name = "username", nullable = false, length = 100)
    private String username;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "email", unique = true, length = 100)
    private String email;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "address", length = 500)
    private String address;

//    @Column(name = "role", nullable = false, length = 20)
//    private String role = "CUSTOMER"; // default role
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private UserRole role = UserRole.CUSTOMER;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;
    @Column(name = "status", nullable = false)
    private Boolean status = true;

    @Column(name = "created_date", columnDefinition = "DATETIME DEFAULT GETDATE()")
    private LocalDateTime createdDate;

    @Column(name = "updated_date", columnDefinition = "DATETIME DEFAULT GETDATE()")
    private LocalDateTime updatedDate;
}

