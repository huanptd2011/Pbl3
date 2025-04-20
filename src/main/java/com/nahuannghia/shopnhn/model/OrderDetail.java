package com.nahuannghia.shopnhn.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {

    @EmbeddedId
    private OrderDetailId orderDetailId;

    @MapsId("orderId")
    @ManyToOne
    @JoinColumn(name = "orderId", nullable = false)
    private Order order;

    @MapsId("productId")
    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "color", insertable = false, updatable = false)
    private String color;

    @Column(name = "size", insertable = false, updatable = false)
    private String size;
}
