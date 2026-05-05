package com.fst.cabinet.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import com.fst.cabinet.entity.Medecin;
import com.fst.cabinet.entity.Patient;
import com.fst.cabinet.entity.RendezVous;
import com.fst.cabinet.service.MedecinService;
import java.util.Map;
import java.time.LocalDateTime;

import com.fst.cabinet.service.PatientService;
import com.fst.cabinet.service.RendezVousService;



@Controller
public class MedecinController {

    private final MedecinService medecinService;
    private final PatientService patientService;
    private final RendezVousService rendezVousService;
    

    public MedecinController(
            MedecinService medecinService,
            PatientService patientService,
            RendezVousService rendezVousService
    ) {
        this.medecinService = medecinService;
        this.patientService = patientService;
        this.rendezVousService = rendezVousService;
    }




    // LIST
    @GetMapping("/medecins")
    public String list(Model model) {
         List<Medecin> medecins = medecinService.getAllMedecins();

    Map<Long, Long> patientCounts = new HashMap<>();

    for (Medecin m : medecins) {
        patientCounts.put(
            m.getId(),
            rendezVousService.countPatientsByMedecin(m.getId())
        );
    }

    model.addAttribute("medecins", medecins);
    model.addAttribute("patientCounts", patientCounts);
    model.addAttribute("activePage", "medecins");

    return "medecins/list";
    }

    // SHOW ADD FORM
    @GetMapping("/medecins/add")
    public String addForm(Model model) {
        model.addAttribute("medecin", new Medecin());
        model.addAttribute("activePage", "medecins");
        return "medecins/add";
    }

    // SAVE (CREATE + UPDATE)
    @PostMapping("/medecins/save")
    public String save(@ModelAttribute Medecin medecin) {
        medecinService.saveMedecin(medecin);
        return "redirect:/medecins";
    }

    // EDIT
    @GetMapping("/medecins/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("medecin", medecinService.getById(id));
        model.addAttribute("activePage", "medecins");
        return "medecins/add";
    }

    // DELETE
    @GetMapping("/medecins/delete/{id}")
    public String delete(@PathVariable Long id) {
        medecinService.deleteMedecin(id);
        return "redirect:/medecins";
    }

    @GetMapping("/medecins/search")
    public String searchMedecins(@RequestParam("keyword") String keyword, Model model) {

        model.addAttribute("medecins", medecinService.searchMedecins(keyword));
        model.addAttribute("keyword", keyword);
        model.addAttribute("activePage", "medecins");
        return "medecins/list";
}

    @GetMapping("/medecins/{id}")
    public String fiche(@PathVariable Long id, Model model) {
        model.addAttribute("medecin", medecinService.getById(id));
        model.addAttribute("activePage", "medecins");
        return "medecins/fiche";
}

    @GetMapping("/medecin/dashboard")
public String dashboard(Model model, Authentication auth) {

    LocalDate today = LocalDate.now();

    LocalDateTime start = today.atStartOfDay();
    LocalDateTime end = today.plusDays(1).atStartOfDay();


    List<RendezVous> todayRdvs =
            rendezVousService.findByDateBetween(start, end);

    List<Patient> recentPatients =
            patientService.getRecentPatients();

    model.addAttribute("todayRdvs", todayRdvs);
    model.addAttribute("todayCount", todayRdvs.size());
    model.addAttribute("recentPatients", recentPatients);

    return "medecin/dashboard";
}
    
}