package com.fst.cabinet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/medecin/dashboard")
    public String medecinDashboard() {
        return "medecin/dashboard";
    }

    @GetMapping("/secretaire/dashboard")
    public String secretaireDashboard() {
        return "secretaire/dashboard";
    }

    @GetMapping("/patient/dashboard")
    public String patientDashboard() {
        return "patient/dashboard"; // you can show "not ready yet"
    }
}