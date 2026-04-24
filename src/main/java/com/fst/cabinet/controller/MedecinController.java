package com.fst.cabinet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fst.cabinet.entity.Medecin;
import com.fst.cabinet.service.MedecinService;

@Controller
public class MedecinController {

    private final MedecinService medecinService;

    public MedecinController(MedecinService medecinService) {
        this.medecinService = medecinService;
    }

    // LIST
    @GetMapping("/medecins")
    public String list(Model model) {
        model.addAttribute("medecins", medecinService.getAllMedecins());
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


    
}