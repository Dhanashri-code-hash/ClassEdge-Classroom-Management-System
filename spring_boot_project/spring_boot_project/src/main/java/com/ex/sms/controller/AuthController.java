package com.ex.sms.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate(); // Invalidates the session, clearing all attributes
            System.out.println("Session invalidated for user."); // For logging
        }

        redirectAttributes.addFlashAttribute("successMessage", "You have been logged out successfully!");
        return "redirect:/login"; // Redirect to your login page
    }
}