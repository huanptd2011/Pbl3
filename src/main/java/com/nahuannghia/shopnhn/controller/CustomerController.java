package com.nahuannghia.shopnhn.controller;

import com.nahuannghia.shopnhn.model.Customer;
import com.nahuannghia.shopnhn.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @PostMapping
    public Customer addCustomer(@RequestBody Customer customer){
        return customerService.addCustomer(customer);
    }
    @GetMapping
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }
    @GetMapping("/{customerId}")
    public Customer getCustomer(@PathVariable String customerId){
        return customerService.getCustomer(customerId);
    }
    @PutMapping("/{customerId}")
    public Customer updateCustomer(@PathVariable String customerId, @RequestBody Customer customer){
        return customerService.updateCustomer(customerId, customer);
    }
    @DeleteMapping("/{customerId}")
    public void deleteCustomer(@PathVariable String customerId){
        customerService.deleteCustomer(customerId);
    }
}
