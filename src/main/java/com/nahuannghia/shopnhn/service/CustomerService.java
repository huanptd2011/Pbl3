package com.nahuannghia.shopnhn.service;

import com.nahuannghia.shopnhn.model.Customer;
import com.nahuannghia.shopnhn.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    public Customer addCustomer(Customer customer){
        try {
            return customerRepository.save(customer);
        } catch (Exception e){
            throw new RuntimeException("Error adding");
        }
    }
    public List<Customer> getAllCustomers(){
        List<Customer> list = customerRepository.findAll();
        if(list.isEmpty())
            throw new RuntimeException("No customer");
        return list;
    }
    public Customer getCustomer(String customerId){
        return customerRepository.findById(customerId).orElseThrow(()->
                new RuntimeException("Not found"));
    }
    public Customer updateCustomer(String customerId, Customer customer){
        Customer existing = getCustomer(customerId);
        existing.setCustomerAddress(customer.getCustomerAddress());
        existing.setCustomerDOB(customer.getCustomerDOB());
        existing.setCustomerEmail(customer.getCustomerEmail());
        existing.setCustomerGender(customer.getCustomerGender());
        existing.setCustomerPhone(customer.getCustomerPhone());
        existing.setCustomerName(customer.getCustomerName());
        try{
            return customerRepository.save(existing);
        } catch (Exception e){
            throw new RuntimeException("Error updating");
        }
    }
    public void deleteCustomer(String customerId){
        Customer existing = getCustomer(customerId);
        try{
            customerRepository.delete(existing);
        } catch (Exception e){
            throw new RuntimeException("Error deleting");
        }
    }
}
