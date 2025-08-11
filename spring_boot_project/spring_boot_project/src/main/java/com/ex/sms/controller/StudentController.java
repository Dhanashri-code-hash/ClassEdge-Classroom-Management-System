package com.ex.sms.controller;

import com.ex.sms.entity.*;
import com.ex.sms.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired private StudentService studentService;
    @Autowired private AssignmentService assignmentService;
    @Autowired private NotesService notesService;

    @GetMapping("/dashboard")
    public String studentDashboard(Model model, HttpSession session) {
        Long studentId = (Long) session.getAttribute("loggedInStudentId");
        Student student = null;

        if (studentId != null) {
            student = studentService.getStudentById(studentId);
        }

        if (student == null) {
            return "redirect:/login?error=not_logged_in";
        }

        model.addAttribute("student", student);
        model.addAttribute("assignments", assignmentService.getAssignmentsByStudentId(student.getId()));
        model.addAttribute("notes", notesService.getAllNotes());

        model.addAttribute("studentName", student.getName());

        return "student/dashboard"; // <--- CHANGE THIS LINE!
                                   // This now correctly points to src/main/resources/templates/student/dashboard.html
    }

    @PostMapping("/login")
    public String login(@RequestParam String emailOrUsername,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {
        Student student = studentService.login(emailOrUsername, password);
        if (student != null) {
            session.setAttribute("loggedInStudentId", student.getId());
            session.setAttribute("loggedInStudentName", student.getName());
            return "redirect:/student/dashboard";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid student email or password.");
            return "redirect:/login";
        }
    }

    @PostMapping("/submit-assignment")
    public String submitAssignment(@RequestParam("studentId") Long studentId,
                                   @RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        try {
            Student student = studentService.getStudentById(studentId);
            if (student == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Student not found.");
                return "redirect:/student/dashboard";
            }

            String uploadDir = System.getProperty("user.dir") + "/uploads/assignments/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            File dest = new File(uploadDir + file.getOriginalFilename());
            file.transferTo(dest);

            Assignment assignment = new Assignment();
            assignment.setFileName(file.getOriginalFilename());
            assignment.setUploadDate(LocalDate.now());
            assignment.setStudent(student);
            assignmentService.saveAssignment(assignment);

            redirectAttributes.addFlashAttribute("successMessage", "Assignment submitted successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to upload assignment: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred: " + e.getMessage());
        }
        return "redirect:/student/dashboard";
    }
}