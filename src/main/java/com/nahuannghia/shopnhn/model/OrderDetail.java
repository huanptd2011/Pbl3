package com.nahuannghia.shopnhn.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "order_detail")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {

    @ManyToOne
    @JoinColumn(name = "orderId", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "color", length = 30)
    private String color;

    @Column(name = "size", length = 5)
    private String size;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    // Composite Primary Key
    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderDetailId implements Serializable {
        private Integer orderId;
        private Integer productId;
        private String color;
        private String size;
    }
}
