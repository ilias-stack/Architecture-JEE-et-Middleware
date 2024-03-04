package com.example.jpaenset;

import com.example.jpaenset.entities.Role;
import com.example.jpaenset.entities.User;
import com.example.jpaenset.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpaEnsetApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaEnsetApplication.class, args);
    }

    @Bean
    CommandLineRunner start(UserService userService){
        return args -> {
            userService.addNewUser(new User(null,"Ilias","SecretPass"));
            userService.addNewUser(new User(null,"Admin","12345678"));

            Role studentRole = new Role(null,"Student role without privileges","student");
            Role adminRole = new Role(null,"Admin role with all privileges","admin");
            userService.addNewRole(studentRole);
            userService.addNewRole(adminRole);

            userService.addRoleToUser("Ilias","student");
            userService.addRoleToUser("Admin","admin");
        };
    }

}
