package com.nahuannghia.shopnhn.service;

import com.nahuannghia.shopnhn.model.ProductSize;
import com.nahuannghia.shopnhn.model.ProductSizeKey;
import com.nahuannghia.shopnhn.repository.ProductSizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<ProductSize> getAllProductSize(){
        List<ProductSize> listSize = productSizeRepository.findAll();
        if(listSize.isEmpty()){
            throw new RuntimeException("No size");
        }
        return listSize;
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
