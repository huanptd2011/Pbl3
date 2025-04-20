package com.nahuannghia.shopnhn.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailId {
    private Integer orderId;
    private Integer productId;
    @Column(name = "color", length = 30)
    private String color;

    @Column(name = "size", length = 5)
    private String size;
}
