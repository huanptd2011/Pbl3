package com.nahuannghia.shopnhn.service;

import com.nahuannghia.shopnhn.model.Product;
import com.nahuannghia.shopnhn.model.PromotionProduct;
import com.nahuannghia.shopnhn.model.PromotionProductKey;
import com.nahuannghia.shopnhn.repository.PromotionProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionProductService {
    @Autowired
    private PromotionProductRepository promotionProductRepository;
    public PromotionProduct addPromotionProduct(PromotionProduct promotionProduct){
        try {
            return promotionProductRepository.save(promotionProduct);
        } catch (Exception e){
            throw new RuntimeException("Error adding promotion product");
        }
    }
    public List<PromotionProduct> getAllPromotionProduct(){
        List<PromotionProduct> list = promotionProductRepository.findAll();
        if(list.isEmpty())
            throw new RuntimeException("No promotions product");
        return list;
    }
    public PromotionProduct getPromotionProduct(PromotionProductKey promotionProductKey){
        return promotionProductRepository.findById(promotionProductKey).orElseThrow(() ->
                new RuntimeException("Not found"));
    }
    public PromotionProduct updatePromotionProduct(PromotionProduct promotionProduct, PromotionProductKey promotionProductKey){
        PromotionProduct existing = getPromotionProduct(promotionProductKey);
        existing.setProduct(promotionProduct.getProduct());
        existing.setPromotion(promotionProduct.getPromotion());
        existing.setDiscountPrice(promotionProduct.getDiscountPrice());
        try{
            return promotionProductRepository.save(existing);
        } catch (Exception e){
            throw new RuntimeException("Error updating");
        }
    }
    public void deletePromotionProduct(PromotionProductKey promotionProductKey){
        PromotionProduct existing = getPromotionProduct(promotionProductKey);
        try {
            promotionProductRepository.delete(existing);
        } catch (Exception e){
            throw new RuntimeException("Error deleting");
        }
    }
}
