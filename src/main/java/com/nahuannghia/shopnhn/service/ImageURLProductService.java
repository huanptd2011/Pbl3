package com.nahuannghia.shopnhn.service;

import com.nahuannghia.shopnhn.model.ImageURLProduct;
import com.nahuannghia.shopnhn.model.ImageURLProductKey;
import com.nahuannghia.shopnhn.repository.ImageURLProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageURLProductService {
    @Autowired
    private ImageURLProductRepository imageURLProductRepository;
    public ImageURLProduct addImageURLProduct(ImageURLProduct imageURLProduct){
        try{
            return imageURLProductRepository.save(imageURLProduct);
        } catch (Exception e){
            throw new RuntimeException("Error adding");
        }
    }
    public List<ImageURLProduct> getAllImageURLProduct(){
        List<ImageURLProduct> list = imageURLProductRepository.findAll();
        if(list.isEmpty())
            throw new RuntimeException("No image product");
        return list;
    }
    public ImageURLProduct getImageURLProduct(ImageURLProductKey imageURLProductKey){
        return imageURLProductRepository.findById(imageURLProductKey).orElseThrow(()->
                new RuntimeException("Not found"));
    }
    public ImageURLProduct updateImageURLProduct(ImageURLProductKey imageURLProductKey, ImageURLProduct imageURLProduct){
        ImageURLProduct existing = getImageURLProduct(imageURLProductKey);
        existing.setProduct(imageURLProduct.getProduct());
        existing.setImageURL(imageURLProduct.getImageURL());
        try{
            return imageURLProductRepository.save(existing);
        } catch (Exception e){
            throw new RuntimeException("Error updating");
        }
    }
    public void deleteImageURLProduct(ImageURLProductKey imageURLProductKey){
        ImageURLProduct existing = getImageURLProduct(imageURLProductKey);
        try {
            imageURLProductRepository.delete(existing);
        } catch (Exception e){
            throw new RuntimeException("Error deleting");
        }
    }
}
