package com.nahuannghia.shopnhn.service;

import com.nahuannghia.shopnhn.Response.ProductSizeResponse;
import com.nahuannghia.shopnhn.model.Product;
import com.nahuannghia.shopnhn.model.ProductSize;
import com.nahuannghia.shopnhn.model.ProductSizeKey;
import com.nahuannghia.shopnhn.repository.ProductSizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductSizeService {
    @Autowired
    ProductSizeRepository productSizeRepository;
    public ProductSize addProductSize(ProductSize productSize){
        try{
            return productSizeRepository.save(productSize);
        } catch (Exception e){
            throw new RuntimeException("Error adding");
        }
    }

    public ProductSizeResponse getAllProductSize(String productId){

        List<ProductSize> productSizes = productSizeRepository.findByProduct_ProductId(productId);
        if (productSizes == null || productSizes.isEmpty()) {
            return null;
        }
        Product product = productSizes.get(0).getProduct();
        List<String> sizes = productSizes.stream()
                .map(ProductSize::getSize)
                .distinct()
                .collect(Collectors.toList());

        ProductSizeResponse response = new ProductSizeResponse();
        response.setProduct(product);
        response.setSizes(sizes);
        return response;
    }

    public ProductSize getProductSize(ProductSizeKey productSizeKey){
        return productSizeRepository.findById(productSizeKey).orElseThrow(()->
                new RuntimeException("Not found"));
    }

    public ProductSize updateProductSize(ProductSize productSize, ProductSizeKey productSizeKey){
        ProductSize existingProductSize = getProductSize(productSizeKey);
        existingProductSize.setProduct(productSize.getProduct());
        try{
            return productSizeRepository.save(existingProductSize);
        } catch (Exception e){
            throw new RuntimeException("Error updating");
        }
    }

    public void deleteProductSize(ProductSizeKey productSizeKey){
        ProductSize existingProductSize = getProductSize(productSizeKey);
        try{
            productSizeRepository.delete(existingProductSize);
        } catch (Exception e){
            throw new RuntimeException("Error deleting");
        }
    }
}
