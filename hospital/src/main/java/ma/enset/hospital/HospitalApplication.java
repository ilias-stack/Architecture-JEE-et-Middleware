package ma.enset.hospital;

import ma.enset.hospital.entities.*;
import ma.enset.hospital.repositories.ConsultationRepository;
import ma.enset.hospital.repositories.MedecinRepository;
import ma.enset.hospital.repositories.PatientRepository;
import ma.enset.hospital.repositories.RendezVousRepository;
import ma.enset.hospital.service.HospitalServiceImpl;
import ma.enset.hospital.service.IHospitalService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class HospitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalApplication.class, args);
	}

	@Bean
	CommandLineRunner start(IHospitalService hospitalService){
		return args -> {
			Stream.of("Hassan","Ilias","Ismail").forEach(s -> {
				hospitalService.savePatient(new Patient(null,s,new Date(),true,null));
			});

			Stream.of("Mohammed","Adil","Souad").forEach(s -> {
				hospitalService.saveMedecin(new Medecin(null,s,s+"@email.com",Math.random()>0.5f ? "Generaliste" : "Specialiste",null));
			});

			Patient p = hospitalService.findPatientByNom("Ilias");
			Medecin m = hospitalService.findMedecinByNom("Souad");
			hospitalService.saveRendezVous(new RendezVous(null, new Date(),StatusRDV.PENDING,p,m,null));

			RendezVous rendezVous = rendezVousRepository.findById(1L).get();
			hospitalService.saveConsultation(new Consultation(null,new Date(),"Rapport",rendezVous));

		};
	}

}
