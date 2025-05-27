package com.nahuannghia.shopnhn.request;

import jdk.jfr.Category;

import java.math.BigDecimal;
import java.util.List;

import com.nahuannghia.shopnhn.model.ProductCategory;

public class ProductRequest {
    private String productName;
    private String productDescription;
    private String brand;
    private BigDecimal price;
    private List<ProductInventoryRequest> sizeColorList;
    private List<ProductImageRequest> imageList;
    private ProductCategory category;

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategoryId(ProductCategory category) {
        this.category = category;
    }

    public Integer getCategoryId() {
        return category.getCategoryId();
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<ProductInventoryRequest> getSizeColorList() {
        return sizeColorList;
    }

    public void setSizeColorList(List<ProductInventoryRequest> sizeColorList) {
        this.sizeColorList = sizeColorList;
    }

    public List<ProductImageRequest> getImageList() {
        return imageList;
    }

    public void setImageList(List<ProductImageRequest> imageList) {
        this.imageList = imageList;
    }

    // Constructor

    public ProductRequest() {
    }

    public ProductRequest(String productName, String productDescription, String brand, BigDecimal price,
            List<ProductInventoryRequest> sizeColorList, List<ProductImageRequest> imageList,
            ProductCategory category) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.brand = brand;
        this.price = price;
        this.sizeColorList = sizeColorList;
        this.imageList = imageList;
        this.category = category;
    }

    //

}
