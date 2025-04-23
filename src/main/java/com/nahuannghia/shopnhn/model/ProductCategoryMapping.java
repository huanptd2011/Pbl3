package com.nahuannghia.shopnhn.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Entity
@Table(name = "productCategoryMapping")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryMapping {

    @EmbeddedId
    private ProductCategoryMappingId id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "categoryId")
    private ProductCategory category;

    // Composite primary key class, nested
    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductCategoryMappingId implements Serializable {

        @Column(name = "productId")
        private Integer productId;

        @Column(name = "categoryId")
        private Integer categoryId;
    }
}
