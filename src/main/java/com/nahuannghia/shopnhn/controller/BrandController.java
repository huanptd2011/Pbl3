package com.nahuannghia.shopnhn.controller;

import com.nahuannghia.shopnhn.model.Brand;
import com.nahuannghia.shopnhn.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brands")
public class BrandController {
    @Autowired
    private BrandService brandService;
    @PostMapping
    public Brand addBrand(@RequestBody Brand brand){
        return brandService.addBrand(brand);
    }
    @GetMapping
    public List<Brand> getAllBrands(){
        return brandService.getAllBrands();
    }
    @GetMapping("/{brandId}")
    public Brand getBrand(@PathVariable String brandId){
        return brandService.getBrand(brandId);
    }
    @PutMapping("/{brandId}")
    public Brand updateBrand(@PathVariable String brandId, @RequestBody Brand brand){
        return brandService.updateBrand(brandId, brand);
    }
    @DeleteMapping("/{brandId}")
    public void deleteBrand(@PathVariable String brandId){
        brandService.deleteBrand(brandId);
    }
}
