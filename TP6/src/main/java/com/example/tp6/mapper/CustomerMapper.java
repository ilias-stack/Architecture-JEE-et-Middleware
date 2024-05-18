package com.example.tp6.mapper;

import com.example.tp6.dto.CustomerRequest;
import com.example.tp6.entities.Customer;
import com.example.tp6.stub.CustomerServiceOuterClass;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    private ModelMapper modelMapper=new ModelMapper();
    public Customer fromCustomerRequest(CustomerRequest customerRequest){
        return modelMapper.map(customerRequest, Customer.class);
    }
    public CustomerServiceOuterClass.Customer fromCustomer(Customer customer){
        return modelMapper.map(customer,CustomerServiceOuterClass.Customer.Builder.class).build();
    }

    public Customer fromGrpcCustomerRequest(CustomerServiceOuterClass.CustomerRequest customerRequest){
        return modelMapper.map(customerRequest,Customer.class);
    }


}
