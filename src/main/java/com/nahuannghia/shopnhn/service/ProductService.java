package com.nahuannghia.shopnhn.service;

import com.nahuannghia.shopnhn.model.Product;
import com.nahuannghia.shopnhn.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public Product addProduct(Product product){
        try {
            return productRepository.save(product);
        } catch (Exception e){
            throw new RuntimeException("Error adding");
        }
    }
    public List<Product> getAllProducts(){
        List<Product> list = productRepository.findAll();
        if(list.isEmpty())
            throw new RuntimeException("No product");
        return list;
    }
    public Product getProduct(String productId){
        return productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Not found"));
    }
    public void deleteProduct(String productId){
        if(productRepository.existsById(productId))
            productRepository.delete(getProduct(productId));
        else
            throw new RuntimeException("Error deleting");
    }
    public Product updateProduct(String productId ,Product product){
        Product productUpdate = getProduct(productId);
        productUpdate.setProductName(product.getProductName());
        productUpdate.setProductDescription(product.getProductDescription());
        productUpdate.setImageUrlId(product.getImageUrlId());
        productUpdate.setBrand(product.getBrand());
        productUpdate.setPrice(product.getPrice());
        productUpdate.setDiscount(product.getDiscount());
        productUpdate.setCreatedAt(product.getCreatedAt());
        productUpdate.setUpdatedAt(product.getUpdatedAt());
        try {
            return productRepository.save(productUpdate);
        } catch (Exception e) {
            throw new RuntimeException("Error updating");
        }
    }
}
