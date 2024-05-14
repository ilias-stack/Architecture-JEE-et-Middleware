package org.sid.ebankingbackend.security;

import org.sid.ebankingbackend.entities.Customer;
import org.sid.ebankingbackend.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        String[] roles = customer.getRoles().split(",");

        return org.springframework.security.core.userdetails.User
                .withUsername(customer.getEmail())
                .password(customer.getPassword())
                .roles(roles)
                .build();
    }
}
