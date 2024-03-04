package com.example.jpaenset.repositories;

import com.example.jpaenset.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
    User findUserByUsername(String username);
}
