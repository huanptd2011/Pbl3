package com.nahuannghia.shopnhn.service;

import com.nahuannghia.shopnhn.model.Brand;
import com.nahuannghia.shopnhn.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class BrandService {
    @Autowired
    private BrandRepository brandRepository;
    public Brand addBrand(Brand brand){
        try {
            return brandRepository.save(brand);
        } catch (Exception e){
            throw new RuntimeException("Error adding");
        }
    }
    public List<Brand> getAllBrands(){
        List<Brand> list = brandRepository.findAll();
        if(list.isEmpty())
            throw new RuntimeException("No Brand");
        return list;
    }
    public Brand getBrand(String brandId){
        return brandRepository.findById(brandId).orElseThrow(() -> new RuntimeException("Not Found"));
    }
    public void deleteBrand(String brandId){
        if(brandRepository.existsById(brandId))
            brandRepository.delete(getBrand(brandId));
        else
            throw new RuntimeException("Not delete");
    }
    public Brand updateBrand(String brandId ,Brand brand){
        Brand brandUpdate = getBrand(brandId);
        brandUpdate.setBrandName(brand.getBrandName());
        brandUpdate.setBrandDescription(brandUpdate.getBrandDescription());
        brandUpdate.setBrandAddress(brandUpdate.getBrandAddress());
        brandUpdate.setBrandEmail(brand.getBrandEmail());
        brandUpdate.setBrandPhone(brandUpdate.getBrandPhone());
        brandUpdate.setBrandImage(brandUpdate.getBrandImage());
        try {
            return brandRepository.save(brandUpdate);
        } catch (Exception e){
            throw new RuntimeException("Error updating");
        }
    }
}
