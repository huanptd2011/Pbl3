package com.nahuannghia.shopnhn.service;

import com.nahuannghia.shopnhn.model.ProductColor;
import com.nahuannghia.shopnhn.model.ProductColorKey;
import com.nahuannghia.shopnhn.repository.ProductColorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductColorService {
    @Autowired
    private ProductColorRepository productColorRepository;
    public ProductColor addColor(ProductColor productColor){
        try{
            return productColorRepository.save(productColor);
        } catch (Exception e){
            throw new RuntimeException("Error adding");
        }
    }

    public List<ProductColor> getAllProductColors(){
        List<ProductColor> listColor = productColorRepository.findAll();
        if(listColor.isEmpty()){
            throw new RuntimeException("No color");
        }
        return listColor;
    }

    public ProductColor getProductColor(ProductColorKey productColorKey){
        return productColorRepository.findById(productColorKey)
                .orElseThrow(() ->
                new RuntimeException("Not found"));
    }

    public ProductColor updateColor(ProductColor productColor, ProductColorKey productColorKey){
        ProductColor existingProductColor = getProductColor(productColorKey);

        existingProductColor.setColor(productColor.getColor());
        existingProductColor.setImage(productColor.getImage());
        existingProductColor.setImageUrlProductColor(productColor.getImageUrlProductColor());

        try{
            return productColorRepository.save(existingProductColor);
        } catch (Exception e){
            throw new RuntimeException("Error updating");
        }
    }

    @Transactional
    public void deleteProductColor(ProductColorKey productColorKey){
        ProductColor existingProductColor = getProductColor(productColorKey);
        try{
            productColorRepository.delete(existingProductColor);
        } catch (Exception e){
            throw new RuntimeException("Error deleting");
        }
    }
}
