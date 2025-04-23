package com.nahuannghia.shopnhn.model;

import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Table(name = "productCategory")

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryId")
    private Integer categoryId;

    @Column(name = "categoryName", nullable = false, length = 100)
    private String categoryName;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

//    @ManyToMany(mappedBy = "categories")
//    private Set<Product> products;

}
