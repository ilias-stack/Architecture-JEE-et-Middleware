package com.example.tp3;

import com.example.tp3.entities.Patient;
import com.example.tp3.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class Tp3Application {

    public static void main(String[] args) {
        SpringApplication.run(Tp3Application.class, args);
    }


    @Bean
    CommandLineRunner start(PatientRepository patientRepository) throws Exception {
        return args -> {
            patientRepository.save(new Patient(null,"Ilias",new Date(),false,19));
            patientRepository.save(new Patient(null,"Sanae",new Date(),true,9));
            patientRepository.save(new Patient(null,"Mohammed",new Date(),false,17));
        };
    }
}
