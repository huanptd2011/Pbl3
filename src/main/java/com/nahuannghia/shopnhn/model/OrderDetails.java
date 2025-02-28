package com.nahuannghia.shopnhn.model;
import java.math.BigDecimal;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

import jakarta.persistence.Transient;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
@Entity
public class OrderDetails {

    @EmbeddedId
    private OrderDetailsKey id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id", nullable = false)
    private Order order; 

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column( name = "quantity", nullable = false)
    private int quantity;

    @Column( name = "unit_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    @Column( name = "unit", length = 20, nullable = false)
    private String unit;

    @Column( name = "discount", precision = 5, scale = 2)
    private BigDecimal discount;

    @Transient // Bỏ qua cột computed TotalPrice
    private BigDecimal totalPrice;
}

