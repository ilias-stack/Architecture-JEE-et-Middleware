package com.example.tp3.web;

import com.example.tp3.entities.Patient;
import com.example.tp3.repositories.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;

    @GetMapping("/index")
    public String index(Model model){
        List<Patient> patients = patientRepository.findAll();
        model.addAttribute("listPatients",patients);
        return "patients";
    }

}
