package com.fst.cabinet.controller;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;
import com.fst.cabinet.entity.Patient;
import com.fst.cabinet.entity.RendezVous;
import com.fst.cabinet.service.MedecinService;
import com.fst.cabinet.service.PatientService;

import com.fst.cabinet.service.RendezVousService;

@Controller
public class PatientController {

    private final PatientService patientService;
    private final MedecinService medecinService;
    private final RendezVousService rendezVousService;

    public PatientController(PatientService patientService,
                             MedecinService medecinService,
                             RendezVousService rendezVousService) {

        this.patientService = patientService;
        this.medecinService = medecinService;
        this.rendezVousService = rendezVousService;
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

    @GetMapping("/patient/patientdashboard")
public String patientDashboard(Model model, Authentication auth) {

    String username = auth.getName();

    List<RendezVous> list =
            rendezVousService.findByPatientUsername(username);

    model.addAttribute("appointments", list);

    return "patient/patientdashboard";
}


    @GetMapping("/patient/dashboard")
public String dashboard(Model model, Authentication authentication) {

    String username = authentication.getName();

    List<RendezVous> rdvs =
            rendezVousService.findByPatientUsername(username);

    model.addAttribute("rdvs", rdvs);

    return "patient/dashboard"; // IMPORTANT: match file path
}
    @GetMapping("/patients/{id}")
    public String patientFiche(@PathVariable Long id, Model model) {

    Patient patient = patientService.getPatientById(id);

    model.addAttribute("patient", patient);

    return "patients/fiche"; // THIS maps to patients/fiche.html
}

    @GetMapping("/patients/edit/{id}")
public String editPatient(@PathVariable Long id, Model model) {

    Patient patient = patientService.getById(id);

    model.addAttribute("patient", patient);

    return "patients/edit"; // this maps to templates/patients/edit.html
}


}


