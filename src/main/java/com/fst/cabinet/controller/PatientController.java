package com.fst.cabinet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import com.fst.cabinet.entity.Patient;
import com.fst.cabinet.entity.RendezVous;
import com.fst.cabinet.service.MedecinService;
import com.fst.cabinet.service.PatientService;

@Controller
public class PatientController {

    private final PatientService patientService;
    private final MedecinService medecinService;

    public PatientController(PatientService patientService,
                             MedecinService medecinService) {
        this.patientService = patientService;
        this.medecinService = medecinService;
    }

    // ================= LIST =================
    @GetMapping("/patients")
    public String listPatients(Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        return "patients/list";
    }

    // ================= ADD PATIENT (ADMIN) =================
    @GetMapping("/patients/add")
    public String showAddForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "patients/add";
    }

    // ================= SAVE PATIENT =================
    @PostMapping("/patients/save")
    public String savePatient(@ModelAttribute Patient patient) {
        patientService.savePatient(patient);
        return "redirect:/patients";
    }



    // ================= PATIENT ADD APPOINTMENT PAGE =================
    @GetMapping("/patient/add")
public String showAddAppointmentPage(Model model) {

    model.addAttribute("appointment", new RendezVous());
    model.addAttribute("medecins", medecinService.getAllMedecins());

    return "patient/add";
}
}