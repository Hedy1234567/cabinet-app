package com.fst.cabinet.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fst.cabinet.entity.RendezVous;
import com.fst.cabinet.service.RendezVousService;

@Controller
public class RendezVousController {

    private final RendezVousService rendezVousService;

    public RendezVousController(RendezVousService rendezVousService) {
        this.rendezVousService = rendezVousService;
    }

    // 📅 DASHBOARD (calendar)
    @GetMapping("/rendezvous")
    public String dashboard(Model model) {
        model.addAttribute("activePage", "rdv");
        return "rendezvous/dashboard";
    }

    // 🔄 EVENTS API
    @GetMapping("/rendezvous/events")
    @ResponseBody
    public List<Map<String, Object>> getEvents() {

        List<RendezVous> rdvs = rendezVousService.getRendezVousForCurrentUser();
        List<Map<String, Object>> events = new ArrayList<>();

        for (RendezVous rdv : rdvs) {

            Map<String, Object> event = new HashMap<>();

            // title
            String title = rdv.getPatient().getNom() + " - " +
                           rdv.getMedecin().getNom();

            // time
            LocalDateTime start = rdv.getDateHeure();
            LocalDateTime end = start.plusMinutes(rdv.getDureeMinutes());

            event.put("id", rdv.getId());
            event.put("title", title);
            event.put("start", start.toString());
            event.put("end", end.toString());

            // 🎨 colors حسب statut
            String color = "#0d6efd";

            switch (rdv.getStatut()) {
                case CONFIRME:
                    color = "#28a745";
                    break;
                case ANNULE:
                    color = "#dc3545";
                    break;
                case PLANIFIE:
                    color = "#ffc107";
                    break;
                case TERMINE:
                    color = "#6c757d";
                    break;
            }

            event.put("color", color);

            events.add(event);
        }

        return events;
    }
}