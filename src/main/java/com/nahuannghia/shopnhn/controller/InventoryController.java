package com.nahuannghia.shopnhn.controller;

import com.nahuannghia.shopnhn.model.Inventory;
import com.nahuannghia.shopnhn.model.Product;
import com.nahuannghia.shopnhn.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;
    @PostMapping
    public Inventory addInventory(@RequestBody Inventory inventory){
        return inventoryService.addInventory(inventory);
    }
    @GetMapping
    public List<Inventory> getAllInventory(){
        return inventoryService.getAllInventory();
    }
    @GetMapping("/{productId}")
    public Inventory getInventory(@PathVariable String productId){
        return inventoryService.getInventory(new Product(productId));
    }
    @PutMapping("/{productId}")
    public Inventory updateInventory(@PathVariable String productId, @RequestBody Inventory inventory){
        return inventoryService.updateInventory(new Product(productId), inventory);
    }
    @DeleteMapping("/{productId}")
    public void deleteInventory(@PathVariable String productId){
        inventoryService.deleteInventory(new Product(productId));
    }
}
