package com.framework.java.mailservice.controller;

import com.framework.java.mailservice.entity.UsersEmail;
import com.framework.java.mailservice.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/registerUser")
public class RegistrationController {

    @Autowired
    private UserRegistrationService userRegistrationService;

    // 🔹 Show registration form
    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UsersEmail());
        return "/registerUser/userRegistration";
    }

    // 🔹 Handle registration
    @PostMapping
    public String registerUser(@ModelAttribute("user") UsersEmail user, Model model) {
        String message = userRegistrationService.registerUser(user);
        model.addAttribute("message", message);
        return "/registerUser/userRegistration";
    }
}
