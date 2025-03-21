package com.nahuannghia.shopnhn.controller;


import com.nahuannghia.shopnhn.model.Promotion;
import com.nahuannghia.shopnhn.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/promotions")
public class PromotionController {
    @Autowired
    private PromotionService promotionService;
    @PostMapping
    public Promotion addPromotion(Promotion promotion){
        return promotionService.addPromotion(promotion);
    }
    @GetMapping
    public List<Promotion> getAllPromotions(){
        return promotionService.getALlPromotions();
    }
    @GetMapping("/{promotionId}")
    public Promotion getPromotion(@PathVariable String promotionId){
        return promotionService.getPromotion(promotionId);
    }
    @PutMapping("/{promotionId}")
    public Promotion updatePromotion(@PathVariable String promotionId ,@RequestBody Promotion promotion){
        return promotionService.updatePromotion(promotionId, promotion);
    }
    @DeleteMapping("/{promotionId}")
    public void deletePromotion(@PathVariable String promotionId){
        promotionService.deletePromotion(promotionId);
    }
}
