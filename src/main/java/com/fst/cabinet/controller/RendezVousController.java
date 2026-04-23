package com.fst.cabinet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fst.cabinet.service.RendezVousService;

@Controller
public class RendezVousController {

    private final RendezVousService rendezVousService;

    public RendezVousController(RendezVousService rendezVousService) {
        this.rendezVousService = rendezVousService;
    }

    @GetMapping("/rendezvous")
    public String list(Model model) {

        model.addAttribute("rendezvous",
                rendezVousService.getRendezVousForCurrentUser());

        return "rendezvous/list";
    }
}