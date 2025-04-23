package com.nahuannghia.shopnhn.request;

import com.nahuannghia.shopnhn.Response.ProductImageResponse;
import com.nahuannghia.shopnhn.Response.ProductInventoryResponse;
import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {
    private String productName;
    private String productDescription;
    private String brand;
    private BigDecimal price;
    private List<ProductInventoryRequest> sizeColorList;
    private List<ProductImageRequest> imageList;
    public String getProductName() {
        return productName;
    }
    public String getProductDescription() {
        return productDescription;
    }
    public String getBrand() {
        return brand;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public List<ProductInventoryRequest> getSizeColorList() {
        return sizeColorList;
    }
    public List<ProductImageRequest> getImageList() {
        return imageList;
    }

    
}

