package com.nahuannghia.shopnhn.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer")
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

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getUserDob() {
        return userDob;
    }

    public void setUserDob(LocalDateTime userDob) {
        this.userDob = userDob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    //
}
