package com.nahuannghia.shopnhn.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nahuannghia.shopnhn.model.Supplier;
import com.nahuannghia.shopnhn.repository.SupplierRepository;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository supplierRepository1;
    
    public List<Supplier> getALlSuppliers(){
        return supplierRepository1.findAll();
    }
    public Optional<Supplier> getSupplierById(Long id){
        return supplierRepository1.findById(id);
    }
    public Supplier createSupplier(Supplier supplier) {
        return supplierRepository1.save(supplier);
    }

    public Supplier updateSupplier(Long id, Supplier updatedSupplier) {
        return supplierRepository1.findById(id).map(supplier -> {
            supplier.setSupplierName(updatedSupplier.getSupplierName());
            supplier.setSupplierAddress(updatedSupplier.getSupplierAddress());
            supplier.setPhoneNumber(updatedSupplier.getPhoneNumber());
            supplier.setEmail(updatedSupplier.getEmail());
            return supplierRepository1.save(supplier);
        }).orElse(null);
    }

    public boolean deleteSupplier(Long id) {
        if (supplierRepository1.existsById(id)) {
            supplierRepository1.deleteById(id);
            return true;
        }
        return false;
    }
}
    