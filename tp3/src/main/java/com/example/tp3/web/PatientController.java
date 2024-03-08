package com.example.tp3.web;

import com.example.tp3.entities.Patient;
import com.example.tp3.repositories.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;

    @GetMapping("/index")
    public String index(Model model, @RequestParam(value = "page"
            ,defaultValue = "0") int pageNumber
            ,@RequestParam(value = "keyword",defaultValue = "") String keyword){
        Page<Patient> page = patientRepository.findByNomContains(keyword,PageRequest.of(pageNumber,5));
        model.addAttribute("listPatients",page.getContent());
        model.addAttribute("pages",new int[page.getTotalPages()]);
        model.addAttribute("currentPage",pageNumber);
        model.addAttribute("keyword",keyword);
        return "patients";
    }

    @GetMapping("/delete")
    private String delete(Long id,int pageNumber,String keyword){
        patientRepository.deleteById(id);
        return "redirect:/index?page="+pageNumber+"&keyword="+keyword;
    }

    @GetMapping("/")
    private String home(){
        return "redirect:/index";
    }

}
