package com.example.tp6.web;

import com.example.tp6.dto.CustomerRequest;
import com.example.tp6.entities.Customer;
import com.example.tp6.mapper.CustomerMapper;
import com.example.tp6.respositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@AllArgsConstructor
public class CustomerGraphQLController {
    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;

    @QueryMapping
    public List<Customer> allCustomers(){
        return customerRepository.findAll();
    }

    @QueryMapping
    public Customer customerById(@Argument Long id){
        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer with id "+id+" not found."));
    }

    @MutationMapping
    public Customer saveCustomer(@Argument CustomerRequest customerRequest){
        return customerRepository.save(customerMapper.fromCustomerRequest(customerRequest));
    }
}
