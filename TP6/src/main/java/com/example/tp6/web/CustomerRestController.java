package com.example.tp6.web;

import com.example.tp6.entities.Customer;
import com.example.tp6.respositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class CustomerRestController {
    private CustomerRepository customerRepository;

    @GetMapping("/customers")
    public List<Customer> findAll(){
        return customerRepository.findAll();
    }

    @GetMapping("/customers/{customerId}")
    public Customer getCustomer(@PathVariable Long customerId){
        return customerRepository.findById(customerId).orElse(null);
    }

    @PostMapping("/customers")
    public Customer saveCustomer(@RequestBody Customer customer){
        customerRepository.save(customer);
        return customer;
    }

}
