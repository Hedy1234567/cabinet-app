package com.fst.cabinet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Model model) {

        model.addAttribute("activePage", "home");

        return "home";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/welcome";
    }


}