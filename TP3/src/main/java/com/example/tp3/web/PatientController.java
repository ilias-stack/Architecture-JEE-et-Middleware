package com.example.tp3.web;

import com.example.tp3.entities.Patient;
import com.example.tp3.repositories.PatientRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.function.Supplier;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;

    @GetMapping("/user/index")
    private String index(Model model, @RequestParam(value = "page"
            ,defaultValue = "0") int pageNumber
            ,@RequestParam(value = "keyword",defaultValue = "") String keyword){
        Page<Patient> page = patientRepository.findByNomContains(keyword,PageRequest.of(pageNumber,5));
        model.addAttribute("listPatients",page.getContent());
        model.addAttribute("pages",new int[page.getTotalPages()]);
        model.addAttribute("currentPage",pageNumber);
        model.addAttribute("keyword",keyword);
        return "patients";
    }

    @GetMapping("/admin/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private String delete(Long id,int pageNumber,String keyword){
        patientRepository.deleteById(id);
        return "redirect:/user/index?page="+pageNumber+"&keyword="+keyword;
    }

    @GetMapping("/")
    private String home(){
        return "redirect:/user/index";
    }

    @GetMapping("/admin/formPatients")
    private String formPatient(Model model){
        model.addAttribute("patient",new Patient());
        return "formPatient";
    }

    @PostMapping("/admin/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private String save(Model model, @Valid Patient patient, BindingResult bindingResult,@RequestParam(defaultValue = "0") int pageNumber,@RequestParam("") String keyword){
        if(bindingResult.hasErrors()) return "formPatient";
        patientRepository.save(patient);
        return "redirect:/user/index?page="+pageNumber+"&keyword="+keyword;
    }

    @GetMapping("/admin/editPatient")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private String editPatient(Model model,Long id,String keyword,int pageNumber) throws Throwable {
        Patient patient = patientRepository.findById(id).orElseThrow((Supplier<Throwable>) () -> new RuntimeException("Patient introuvable"));
        model.addAttribute("patient",patient);
        model.addAttribute("keyword",keyword);
        model.addAttribute("pageNumber",pageNumber);
        return "editPatient";
    }

}
