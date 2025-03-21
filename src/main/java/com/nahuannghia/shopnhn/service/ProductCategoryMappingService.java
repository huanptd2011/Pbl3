package com.nahuannghia.shopnhn.service;

import com.nahuannghia.shopnhn.model.ProductCategoryMapping;
import com.nahuannghia.shopnhn.model.ProductCategoryMappingKey;
import com.nahuannghia.shopnhn.repository.ProductCategoryMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryMappingService {
    @Autowired
    private ProductCategoryMappingRepository productCategoryMappingRepository;
    public ProductCategoryMapping addProCategoryMapping(ProductCategoryMapping productCategoryMapping){
        try{
            return productCategoryMappingRepository.save(productCategoryMapping);
        } catch (Exception e){
            throw new RuntimeException("Error adding");
        }
    }
    public List<ProductCategoryMapping> getAllProCategoryMapping(){
        List<ProductCategoryMapping> list = productCategoryMappingRepository.findAll();
        if(list.isEmpty())
            throw new RuntimeException("No product category mapping");
        return list;
    }
    public ProductCategoryMapping getProCategoryMapping(ProductCategoryMappingKey productCategoryMappingKey){
        return productCategoryMappingRepository.findById(productCategoryMappingKey).orElseThrow(() ->
                new RuntimeException("Not found"));
    }
    public ProductCategoryMapping updateProductCategoryMapping(ProductCategoryMappingKey productCategoryMappingKey, ProductCategoryMapping productCategoryMapping){
        ProductCategoryMapping existing = getProCategoryMapping(productCategoryMappingKey);
        existing.setProduct(productCategoryMapping.getProduct());
        existing.setProductCategory(productCategoryMapping.getProductCategory());
        try{
            return productCategoryMappingRepository.save(existing);
        } catch (Exception e){
            throw new RuntimeException("Error updating");
        }
    }
    public void deleteProCategoryMapping(ProductCategoryMappingKey productCategoryMappingKey){
        ProductCategoryMapping existing = getProCategoryMapping(productCategoryMappingKey);
        try{
            productCategoryMappingRepository.delete(existing);
        } catch (Exception e){
            throw new RuntimeException("Error deleting");
        }
    }
}
