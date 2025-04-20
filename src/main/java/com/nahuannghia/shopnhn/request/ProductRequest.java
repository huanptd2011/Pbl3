package com.nahuannghia.shopnhn.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private String productName;
    private String productDescription;
    private String brand;
    private BigDecimal price;
    private List<ProductInventoryRequest> sizeColorList;
    private List<ProductImageRequest> imageList;
}

