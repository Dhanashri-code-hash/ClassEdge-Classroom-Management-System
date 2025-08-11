package com.ex.sms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Import Model to receive flash attributes
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login"; // Redirect root URL to /login
    }

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "successMessage", required = false) String successMessage,
                                Model model) {
        // Flash attributes (like 'error' and 'successMessage') are automatically added to the Model by Spring.
        // The @RequestParam is useful if you are explicitly passing them as query parameters,
        // but for flash attributes, they will appear in the Model regardless.

        if (error != null) {
            model.addAttribute("error", error); // The specific error message from redirect
        }
        // 'successMessage' will also be in the model if passed by RedirectAttributes

        return "login"; // Loads login.html from templates folder
    }
}