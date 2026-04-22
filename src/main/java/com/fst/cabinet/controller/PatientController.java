package com.fst.cabinet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.fst.cabinet.entity.Patient;
import com.fst.cabinet.service.PatientService;

@Controller
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/patients";
    }

    @GetMapping("/patients")
    public String listPatients(Model model, String keyword) {

        if (keyword != null && !keyword.isBlank()) {
            model.addAttribute("patients", patientService.searchPatients(keyword));
        } else {
            model.addAttribute("patients", patientService.getAllPatients());
        }

        model.addAttribute("keyword", keyword);

        return "patients/list";
    }

    @GetMapping("/patients/add")
    public String showAddForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "patients/add";
    }

    @PostMapping("/patients/save")
    public String savePatient(@ModelAttribute Patient patient, Model model) {
        try {
            patientService.savePatient(patient);
            return "redirect:/patients";
        } catch (RuntimeException e) {
            model.addAttribute("patient", patient);
            model.addAttribute("errorMessage", e.getMessage());

            if (patient.getId() != null) {
                return "patients/edit";
            }
            return "patients/add";
        }
    }

    @GetMapping("/patients/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("patient", patientService.getPatientById(id));
        return "patients/edit";
    }

    @PostMapping("/patients/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return "redirect:/patients";
    }

    @GetMapping("/patients/{id}")
    public String patientDetails(@PathVariable Long id, Model model) {
        model.addAttribute("patient", patientService.getPatientById(id));
        return "patients/details";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
}
}