package com.example.tp6.web;

import com.example.tp6.entities.Customer;
import com.example.tp6.mapper.CustomerMapper;
import com.example.tp6.respositories.CustomerRepository;
import com.example.tp6.stub.CustomerServiceGrpc;
import com.example.tp6.stub.CustomerServiceOuterClass;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class CustomerGrpcController extends CustomerServiceGrpc.CustomerServiceImplBase {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerMapper customerMapper;
    @Override
    public void getAllCustomers(CustomerServiceOuterClass.GetAllCustomersRequest request, StreamObserver<CustomerServiceOuterClass.GetAllCustomersResponse> responseObserver) {
        List<Customer> customers = customerRepository.findAll();
        CustomerServiceOuterClass.GetAllCustomersResponse customersResponse =
                CustomerServiceOuterClass.GetAllCustomersResponse.newBuilder()
                        .addAllCustomers(customers.stream().map(customer -> customerMapper.fromCustomer(customer)).collect(Collectors.toList()))
                        .build();

        responseObserver.onNext(customersResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void getCustomerById(CustomerServiceOuterClass.GetCustomerByIdRequest request, StreamObserver<CustomerServiceOuterClass.GetCustomerByIdResponse> responseObserver) {
        Customer customerEntity = customerRepository.findById((long) request.getId()).get();
        CustomerServiceOuterClass.GetCustomerByIdResponse response = CustomerServiceOuterClass.GetCustomerByIdResponse
                .newBuilder()
                .setCustomer(customerMapper.fromCustomer(customerEntity))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void saveCustomer(CustomerServiceOuterClass.SaveCustomerRequest request, StreamObserver<CustomerServiceOuterClass.SaveCustomerResponse> responseObserver) {
        Customer savedCustomer = customerRepository.save(customerMapper.fromGrpcCustomerRequest(request.getCustomer()));

        CustomerServiceOuterClass.SaveCustomerResponse response = CustomerServiceOuterClass.SaveCustomerResponse.newBuilder()
                .setCustomer(customerMapper.fromCustomer(savedCustomer))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
