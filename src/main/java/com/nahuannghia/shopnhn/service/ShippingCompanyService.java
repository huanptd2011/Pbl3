package com.nahuannghia.shopnhn.service;

import com.nahuannghia.shopnhn.model.ShipingCompany;
import com.nahuannghia.shopnhn.repository.ShippingCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShippingCompanyService {
    @Autowired
    private ShippingCompanyRepository shippingCompanyRepository;
    public ShipingCompany addShippingCompany(ShipingCompany shipingCompany){
        try {
            return shippingCompanyRepository.save(shipingCompany);
        } catch (Exception e){
            throw new RuntimeException("Error adding");
        }
    }
    public List<ShipingCompany> getAllShippingCompany(){
        List<ShipingCompany> list = shippingCompanyRepository.findAll();
        if(list.isEmpty())
            throw new RuntimeException("No shipping company");
        return list;
    }
    public ShipingCompany getShippingCompany(String shippingCompanyId){
        return shippingCompanyRepository.findById(shippingCompanyId).orElseThrow(() ->
                new RuntimeException("Not found"));
    }
    public ShipingCompany updateShippingCompany(String shipComId ,ShipingCompany shipingCompany){
        ShipingCompany existing = getShippingCompany(shipComId);
        existing.setShipingCompanyDescription(shipingCompany.getShipingCompanyDescription());
        existing.setShipingCompanyName(shipingCompany.getShipingCompanyName());
        existing.setShipingCost(shipingCompany.getShipingCost());
        try{
            return shippingCompanyRepository.save(existing);
        } catch (Exception e){
            throw new RuntimeException("Error updating");
        }
    }
    public void deleteShippingCompany(String shippingCompanyId){
        ShipingCompany existing = getShippingCompany(shippingCompanyId);
        try{
            shippingCompanyRepository.delete(existing);
        } catch (Exception e){
            throw new RuntimeException("Error deleting");
        }
    }
}
