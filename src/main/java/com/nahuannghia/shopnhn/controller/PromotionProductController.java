package com.nahuannghia.shopnhn.controller;

import com.nahuannghia.shopnhn.model.PromotionProduct;
import com.nahuannghia.shopnhn.model.PromotionProductKey;
import com.nahuannghia.shopnhn.service.PromotionProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-promotions")

public class PromotionProductController {
    @Autowired
    private PromotionProductService promotionProductService;
    @PostMapping
    public PromotionProduct addPromotionProduct(PromotionProduct promotionProduct){
        return promotionProductService.addPromotionProduct(promotionProduct);
    }
    @GetMapping
    public List<PromotionProduct> getAllPromotionProduct(){
        return promotionProductService.getAllPromotionProduct();
    }
    @GetMapping("/{productId}/{promotionId}")
    public PromotionProduct getPromotionProduct(@PathVariable String productId, @PathVariable String promotionId){
        return promotionProductService.getPromotionProduct(new PromotionProductKey(productId, promotionId));
    }
    @PutMapping("/{productId}/{promotionId}")
    public PromotionProduct updatePromotionProduct(@PathVariable String productId,
                                                   @PathVariable String promotionId,
                                                   @RequestBody PromotionProduct promotionProduct){
        return promotionProductService.updatePromotionProduct(promotionProduct, new PromotionProductKey(productId, promotionId));
    }
    @DeleteMapping("/{productId}/{promotionId}")
    public void deletePromotionProduct(@PathVariable String productId, @PathVariable String promotionId){
        promotionProductService.deletePromotionProduct(new PromotionProductKey(productId, promotionId));
    }
}
