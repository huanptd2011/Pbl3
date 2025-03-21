package com.nahuannghia.shopnhn.controller;

import com.nahuannghia.shopnhn.model.ProductColor;
import com.nahuannghia.shopnhn.model.ProductColorKey;
import com.nahuannghia.shopnhn.service.ProductColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product-colors")
public class ProductColorController {
    @Autowired
    private ProductColorService productColorService;

    @PostMapping
    public ProductColor addProductColor(@RequestBody ProductColor productColor){
        return productColorService.addColor(productColor);
    }

    @GetMapping()
    public List<ProductColor> getAllProductColors(){
        return productColorService.getAllProductColors();
    }

    @GetMapping("/{productId}/{colorId}")
    public ProductColor getProductColor(@PathVariable String productId, @PathVariable String colorId){
        return productColorService.getProductColor(new ProductColorKey(productId, colorId));
    }

    @DeleteMapping("/{productId}/{colorId}")
    public void deleteProductColor(@PathVariable String productId, @PathVariable String colorId){
        productColorService.deleteProductColor(new ProductColorKey(productId, colorId));
    }

    @PutMapping("/{productId}/{colorId}")
    public ProductColor updateProductColor(@PathVariable String productId, @PathVariable String colorId, @RequestBody ProductColor productColor){
        ProductColorKey productColorKey = new ProductColorKey(productId, colorId);
        return productColorService.updateColor(productColor, productColorKey);
    }
}
