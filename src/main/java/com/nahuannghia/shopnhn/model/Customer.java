package com.nahuannghia.shopnhn.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer{

    @Id
    @Column(name = "customerId")
    private Integer customerId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "customerId")
    private User user;

    @Column(name = "userDob")
    private LocalDateTime userDob;

    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "avatar", length = 255)
    private String avatar;
}
