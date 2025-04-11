
package com.nahuannghia.shopnhn.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId")
    private Integer orderId;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "paymentMethod_id", nullable = false)
    private PaymentMethod paymentMethod;

    @Column(name = "order_date", nullable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
    private LocalDateTime orderDate;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "order_state", nullable = false, length = 20)
    private String orderState = "Chờ xác nhận"; // Default value

    @Column(name = "note", length = 2000)
    private String note;

}
