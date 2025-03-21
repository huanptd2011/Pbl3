package com.nahuannghia.shopnhn.controller;

import com.nahuannghia.shopnhn.model.ShipingCompany;
import com.nahuannghia.shopnhn.service.ShippingCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shipping-company")
public class ShippingCompanyController {
    @Autowired
    private ShippingCompanyService shippingCompanyService;
    @PostMapping
    public ShipingCompany addShippingCompany(@RequestBody ShipingCompany shipingCompany){
        return shippingCompanyService.addShippingCompany(shipingCompany);
    }
    @GetMapping
    public List<ShipingCompany> getAllShippingCompany(){
        return shippingCompanyService.getAllShippingCompany();
    }
    @GetMapping("/{shipComId}")
    public ShipingCompany getShippingCompany(@PathVariable String shipComId){
        return shippingCompanyService.getShippingCompany(shipComId);
    }
    @PutMapping("/{shipComId}")
    public ShipingCompany updateShippingCompany(@PathVariable String shipComId ,@RequestBody ShipingCompany shipingCompany){
        return shippingCompanyService.updateShippingCompany(shipComId, shipingCompany);
    }
    @DeleteMapping("/{shipComId}")
    public void deleteShippingCompany(@PathVariable String shipComId){
        shippingCompanyService.deleteShippingCompany(shipComId);
    }
}
