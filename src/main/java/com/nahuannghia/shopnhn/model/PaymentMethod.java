package com.nahuannghia.shopnhn.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "paymentMethod")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paymentMethod_id")
    private Integer paymentMethodId;

    @Column(name = "paymentMethod_name", nullable = false, length = 50)
    private String paymentMethodName;

}
