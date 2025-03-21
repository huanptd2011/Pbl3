package com.nahuannghia.shopnhn.service;

import com.nahuannghia.shopnhn.model.Product;
import com.nahuannghia.shopnhn.model.Promotion;
import com.nahuannghia.shopnhn.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionService {
    @Autowired
    private PromotionRepository promotionRepository;
    public Promotion addPromotion(Promotion promotion){
        try {
            return promotionRepository.save(promotion);
        } catch (Exception e){
            throw new RuntimeException("Error adding promotion");
        }
    }
    public List<Promotion> getALlPromotions(){
        List<Promotion> list = promotionRepository.findAll();
        if(list.isEmpty()){
            throw new RuntimeException("No promotions");
        }
        return list;
    }
    public Promotion getPromotion(String promotionId){
        return promotionRepository.findById(promotionId).orElseThrow(()->
                new RuntimeException("Not found"));
    }
    public Promotion updatePromotion(String promotionId ,Promotion promotion){
        Promotion existingPromotion = getPromotion(promotionId);
        existingPromotion.setPromotionDetails(promotion.getPromotionDetails());
        existingPromotion.setPromotionName(promotion.getPromotionName());
        existingPromotion.setDescription(promotion.getDescription());
        existingPromotion.setStartDate(promotion.getStartDate());
        existingPromotion.setEndDate(promotion.getEndDate());
        existingPromotion.setDiscountRate(promotion.getDiscountRate());
        existingPromotion.setStatus(promotion.isStatus());
        try{
            return promotionRepository.save(existingPromotion);
        } catch (Exception e){
            throw new RuntimeException("Error updating promotion");
        }
    }
    public void deletePromotion(String promotionId){
        Promotion existingPromotion = getPromotion(promotionId);
        try {
            promotionRepository.delete(existingPromotion);
        } catch (Exception e){
            throw new RuntimeException("Error deleting promotion");
        }
    }
}
