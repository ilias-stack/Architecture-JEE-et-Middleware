package com.example.tp6.web;

import com.example.tp6.dto.CustomerRequest;
import com.example.tp6.entities.Customer;
import com.example.tp6.mapper.CustomerMapper;
import com.example.tp6.respositories.CustomerRepository;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import lombok.AllArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@WebService(serviceName = "customer web service")
public class CustomerSoapController {
    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;

    @WebMethod
    public List<Customer> customerList(){
        return customerRepository.findAll();
    }

    @WebMethod
    public Customer customerById(@WebParam(name = "id") Long customerId){
        return customerRepository.findById(customerId).orElse(null);
    }

    @WebMethod
    public Customer saveCustomer(@WebParam CustomerRequest customerRequest){
        return customerRepository.save(customerMapper.fromCustomerRequest(customerRequest));
    }
}
