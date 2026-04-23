package com.fst.cabinet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fst.cabinet.entity.AppUser;
import com.fst.cabinet.entity.Patient;
import com.fst.cabinet.service.UserService;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // LOGIN PAGE
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // WELCOME PAGE
    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    // SHOW SIGNUP PAGE
    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new AppUser());
        return "signup";
    }

    // HANDLE SIGNUP FORM
    @PostMapping("/signup")
    public String register(@ModelAttribute AppUser user,
                         @ModelAttribute Patient patient) {

        userService.register(user, patient);

        return "redirect:/login";
}
}