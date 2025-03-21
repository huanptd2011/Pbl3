package com.nahuannghia.shopnhn.controller;

import com.nahuannghia.shopnhn.model.ImageURLProduct;
import com.nahuannghia.shopnhn.model.ImageURLProductKey;
import com.nahuannghia.shopnhn.service.ImageURLProductService;
import org.aspectj.lang.annotation.DeclareError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/image-product")
public class ImageURLProductController {
    @Autowired
    private ImageURLProductService imageURLProductService;
    @PostMapping
    public ImageURLProduct addImageURLProduct(@RequestBody ImageURLProduct imageURLProduct){
        return imageURLProductService.addImageURLProduct(imageURLProduct);
    }
    @GetMapping
    public List<ImageURLProduct> getAllImageURLProduct(){
        return imageURLProductService.getAllImageURLProduct();
    }
    @GetMapping("/{productId}/{imageId}")
    public ImageURLProduct getImageURLProduct(@PathVariable String productId, @PathVariable String imageId){
        return imageURLProductService.getImageURLProduct(new ImageURLProductKey(imageId, productId));
    }
    @PutMapping("/{productId}/{imageId}")
    public ImageURLProduct updateImageURLProduct(@PathVariable String productId,
                                                 @PathVariable String imageId,
                                                 @RequestBody ImageURLProduct imageURLProduct){
        return imageURLProductService.updateImageURLProduct(new ImageURLProductKey(imageId, productId), imageURLProduct);
    }
    @DeleteMapping("/{productId}/{imageId}")
    public void deleteImageURLProduct(@PathVariable String productId, @PathVariable String imageId){
        imageURLProductService.deleteImageURLProduct(new ImageURLProductKey(imageId, productId));
    }
}
