package com.nahuannghia.shopnhn.Response;

import com.nahuannghia.shopnhn.model.Product;

import java.util.List;

public class ProductSizeResponse {
    private String message;
    private Product product;
    List<String> sizes;

    public ProductSizeResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<String> getSizes() {
        return sizes;
    }

    public void setSizes(List<String> sizes) {
        this.sizes = sizes;
    }

    public ProductSizeResponse(String message, Product product, List<String> sizes) {
        this.message = message;
        this.product = product;
        this.sizes = sizes;
    }

}
