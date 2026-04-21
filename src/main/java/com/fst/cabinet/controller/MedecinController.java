package com.fst.cabinet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fst.cabinet.entity.Medecin;
import com.fst.cabinet.service.MedecinService;

@Controller
@RequestMapping("/medecins")
public class MedecinController {

    private final MedecinService medecinService;

    public MedecinController(MedecinService medecinService) {
        this.medecinService = medecinService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("medecins", medecinService.getAll());
        return "medecins/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("medecin", new Medecin());
        return "medecins/add";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Medecin medecin) {
        medecinService.save(medecin);
        return "redirect:/medecins";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("medecin", medecinService.getById(id));
        return "medecins/edit";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Medecin medecin) {
        medecinService.update(id, medecin);
        return "redirect:/medecins";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        medecinService.delete(id);
        return "redirect:/medecins";
    }
}