package com.example.tp2;

import com.example.tp2.entities.Patient;
import com.example.tp2.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class Tp2Application  implements CommandLineRunner {

    @Autowired
    private PatientRepository patientRepository;

    public static void main(String[] args) {
        SpringApplication.run(Tp2Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // - Ajouter des patients
        {
            patientRepository.save(new Patient(null, "Mohammed Fadi", new Date(), true, 1));
            patientRepository.save(new Patient(null, "Ilias Zaazaa", new Date(), true, 1));
            patientRepository.save(new Patient(null, "Sami Fadili", new Date(), false, 1));
            patientRepository.save(new Patient(null, "Sami Mendar", new Date(), true, 1));
        }

        // - Consulter tous les patients
        {
            System.out.println("-> Liste des patients.");
            patientRepository.findAll().forEach(patient -> {
                System.out.println(patient.toString());
            });
        }

        // - Consulter un patient
        {
            System.out.println("------ Patient with id:1 ------");
            System.out.println(patientRepository.findById(1L).toString());
        }

        // - Mettre à jour un patient
        {
            Patient p1=patientRepository.findById(1L).orElse(null);
            assert p1 != null;
            p1.setMalade(false);
            System.out.println("------ Patient with id:1 after updating malade ------");
            System.out.println(p1.toString());
        }

        // - Supprimer un patient
        {
            patientRepository.delete(patientRepository.findById(1L).orElseThrow(null));
            System.out.println("-> Liste des patients apres suppression.");
            patientRepository.findAll().forEach(patient -> {
                System.out.println(patient.toString());
            });
        }



    }
}
