package com.nahuannghia.shopnhn.service;

import com.nahuannghia.shopnhn.model.Inventory;
import com.nahuannghia.shopnhn.model.Product;
import com.nahuannghia.shopnhn.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;
    public Inventory addInventory(Inventory inventory){
        try {
            return inventoryRepository.save(inventory);
        } catch (Exception e){
            throw new RuntimeException("Error adding");
        }
    }
    public List<Inventory> getAllInventory(){
        List<Inventory> list = inventoryRepository.findAll();
        if(list.isEmpty())
            throw new RuntimeException("No inventory");
        return list;
    }
    public Inventory getInventory(Product product){
        Optional<Inventory> existing = inventoryRepository.findByProduct(product);
        if(!existing.isPresent()){
            throw new RuntimeException("Not found");
        }
        return existing.get();
    }
    public Inventory updateInventory(Product product, Inventory inventory){
        Inventory existing = getInventory(product);
        existing.setLastUpdate(inventory.getLastUpdate());
        existing.setLocation(inventory.getLocation());
        existing.setQuantity(inventory.getQuantity());
        try{
            return inventoryRepository.save(existing);
        }catch (Exception e){
            throw new RuntimeException("Error updating");
        }
    }
    public void deleteInventory(Product product){
        Inventory existing = getInventory(product);
        try{
            inventoryRepository.delete(existing);
        } catch (Exception e){
            throw new RuntimeException("Error deleting");
        }
    }
}
