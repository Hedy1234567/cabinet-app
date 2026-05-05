package com.fst.cabinet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
@Controller
public class DashboardController {

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/dashboard")
public String redirectDashboard(Authentication auth) {

    String role = auth.getAuthorities().toString();

    if (role.contains("ADMIN")) {
        return "redirect:/admin/dashboard";
    }
    if (role.contains("SECRETAIRE")) {
        return "redirect:/secretaire/dashboard";
    }
    if (role.contains("MEDECIN")) {
        return "redirect:/medecin/dashboard";
    }

    return "redirect:/patient/dashboard";
}

    @GetMapping("/secretaire/dashboard")
    public String secretaireDashboard() {
        return "secretaire/dashboard";
    }

  
}