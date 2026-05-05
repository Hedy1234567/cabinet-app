package com.fst.cabinet.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fst.cabinet.entity.RendezVous;
import com.fst.cabinet.entity.StatutRendezVous;
import com.fst.cabinet.repository.MedecinRepository;
import com.fst.cabinet.repository.PatientRepository;

import com.fst.cabinet.service.PatientService;
import com.fst.cabinet.service.RendezVousService;






@Controller
public class RendezVousController {

    private final RendezVousService rendezVousService;
    private final PatientRepository patientRepository;
    private final PatientService patientService;
    private final MedecinRepository medecinRepository;

    public RendezVousController(RendezVousService rendezVousService,
                                 PatientRepository patientRepository,
                                 PatientService patientService,
                                 MedecinRepository medecinRepository) {

        this.rendezVousService = rendezVousService;
        this.patientRepository = patientRepository;
        this.patientService = patientService;
        this.medecinRepository = medecinRepository;
}

    // =========================
    // LIST APPOINTMENTS
    // =========================
    @GetMapping("/appointments")
    public String appointments(Model model, Authentication auth) {

        List<RendezVous> list = rendezVousService.getAllAppointments();

        model.addAttribute("appointments", list);
        model.addAttribute("activePage", "appointments");

        return "appointments/dashboard";
    }

    // =========================
    // ADD FORM
    // =========================
    @GetMapping("/appointments/add")
    public String addAppointmentForm(Model model) {

        model.addAttribute("appointment", new RendezVous());
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("medecins", medecinRepository.findAll());

        return "appointments/add";
    }

    // =========================
    // SAVE APPOINTMENT
    // =========================
    @PostMapping("/appointments/add")
public String saveAppointment(@RequestParam Long patientId,
                              @RequestParam Long medecinId,
                              @RequestParam LocalDateTime dateHeure,
                              @RequestParam Integer dureeMinutes,
                              @RequestParam(required = false) String motif) {

    RendezVous r = new RendezVous();

    r.setPatient(patientRepository.findById(patientId).orElseThrow());
    r.setMedecin(medecinRepository.findById(medecinId).orElseThrow());

    r.setDateHeure(dateHeure);
    r.setDureeMinutes(dureeMinutes);
    r.setMotif(motif);
    r.setStatut(StatutRendezVous.PLANIFIE);

    rendezVousService.save(r);

    return "redirect:/appointments";
}

    


    // =========================
    // CALENDAR VIEW
    // =========================
    @GetMapping("/appointments/calendar")
    public String calendar(Model model) {

        List<RendezVous> list = rendezVousService.getAllAppointments();

        List<Map<String, Object>> events = new ArrayList<>();

        for (RendezVous r : list) {

            Map<String, Object> event = new HashMap<>();

            event.put("title",
                    r.getPatient().getNom() + " - " + r.getMedecin().getNom());

            event.put("start", r.getDateHeure().toString());

            events.add(event);
        }

        model.addAttribute("events", events);

        return "appointments/calendar";
    }

    // =========================
    // DELETE (ADMIN ONLY)
    // =========================
    @PostMapping("/appointments/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PATIENT','MEDECIN','SECRETAIRE')")
    public String deleteAppointment(@PathVariable Long id, Authentication authentication) {

    rendezVousService.deleteById(id);

    boolean isPatient = authentication.getAuthorities().stream()
        .anyMatch(a -> a.getAuthority().equals("ROLE_PATIENT"));

    if (isPatient) {
        return "redirect:/patient/patientdashboard";
    } else {
        return "redirect:/appointments";
    }
}

    // =========================
    // VIEW APPOINTMENT
    // =========================
    @GetMapping("/appointments/{id}")
    public String viewAppointment(@PathVariable Long id, Model model) {

        RendezVous appointment = rendezVousService.findById(id);

        model.addAttribute("appointment", appointment);
        model.addAttribute("activePage", "appointments");

        return "appointments/view";
    }

    // =========================
    // EDIT FORM
    // =========================
    @GetMapping("/appointments/edit/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SECRETAIRE')")
    public String editAppointmentForm(@PathVariable Long id, Model model) {

        RendezVous appointment = rendezVousService.findById(id);

        model.addAttribute("appointment", appointment);
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("medecins", medecinRepository.findAll());

        return "appointments/edit";
        
    }



    // =========================
    // UPDATE APPOINTMENT
    // =========================
    @PostMapping("/appointments/edit/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SECRETAIRE')")
    public String updateAppointment(@PathVariable Long id,
                                    @ModelAttribute RendezVous appointment) {

        RendezVous existing = rendezVousService.findById(id);

        existing.setDateHeure(appointment.getDateHeure());
        existing.setDureeMinutes(appointment.getDureeMinutes());
        existing.setStatut(appointment.getStatut());
        existing.setMotif(appointment.getMotif());

        

        rendezVousService.save(existing);

        return "redirect:/appointments";
    }

    
    
    @PostMapping("/patient/add")
public String saveAppointment(@ModelAttribute RendezVous rdv,
                             Authentication auth) {
    rdv.setStatut(StatutRendezVous.PLANIFIE);

    
    rdv.setPatient(patientService.getCurrentPatient(auth));


    rendezVousService.save(rdv);

    return "redirect:/patient/patientdashboard";
}
    
}