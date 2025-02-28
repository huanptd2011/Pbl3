
package com.nahuannghia.shopnhn.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "[Order]")

public class Order {

    @Id
    @Column( length = 30, nullable = false)
    private String orderId; 

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer; 

    @Column( name = "order_status", length = 50, nullable = false)
    private String orderStatus;

    @Column(name = "total_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalAmount;

    @ManyToOne
    @JoinColumn(name = "payment_method_id", nullable = false)
    private PaymentMethod paymentMethod; // Giả sử có entity PaymentMethod

    @Column( name = "shipping_address", length = 500, nullable = false)
    private String shippingAddress;

    @Column(name = "notes", length = 2000)
    private String notes;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;


    @ManyToOne
    @JoinColumn(name = "shipping_company_id", nullable = false)
    private ShipingCompany shippingCompany; // Giả sử có entity ShippingCompany
}
