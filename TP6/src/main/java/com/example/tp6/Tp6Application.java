package com.example.tp6;

import com.example.tp6.entities.Customer;
import com.example.tp6.respositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Tp6Application {

    public static void main(String[] args) {
        SpringApplication.run(Tp6Application.class, args);
    }

    @Bean
    CommandLineRunner start(CustomerRepository customerRepository){
        return args -> {
            customerRepository.save(Customer.builder().name("Hassan").email("hassan@mail.com").build());
            customerRepository.save(Customer.builder().name("Imane").email("imane@mail.com").build());
            customerRepository.save(Customer.builder().name("Mohammed").email("mohammed@mail.com").build());
        };
    }

}
