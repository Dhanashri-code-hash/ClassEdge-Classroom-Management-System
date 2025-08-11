package com.ex.sms.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession; // Ensure this is imported

import com.ex.sms.entity.*;
import com.ex.sms.service.*;

@Controller
@RequestMapping("/teacher")
public class TeacherContoller { // Typo: Should be TeacherController, but using existing name

    @Autowired private StudentService studentService;
    @Autowired private NotesService notesService;
    @Autowired private AssignmentService assignmentService;
    @Autowired private TeacherService teacherService; // Autowire TeacherService

    @Value("${notes.upload.dir}") // Path for saving/deleting notes
    private String notesUploadDir;

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        String username = (String) session.getAttribute("loggedInTeacherUsername");

        if (username == null) {
            return "redirect:/login?error=not_logged_in"; // Redirect if not logged in
        }

        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("notes", notesService.getAllNotes());
        model.addAttribute("assignments", assignmentService.getAllAssignments());
        model.addAttribute("username", username); // Add username to the model

        return "teacher/dashboard"; // Correct path to template (src/main/resources/templates/teacher/dashboard.html)
    }

    @PostMapping("/login") // Handles POST from the main login page
    public String login(@RequestParam String emailOrUsername, // Assumed to be username for teacher
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {
        Teacher teacher = teacherService.login(emailOrUsername, password);

        if (teacher != null) {
            session.setAttribute("loggedInTeacherUsername", teacher.getUsername());
            // You might also store ID if needed: session.setAttribute("loggedInTeacherId", teacher.getId());
            return "redirect:/teacher/dashboard"; // Success
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid teacher username or password.");
            return "redirect:/login"; // Failure
        }
    }

    @PostMapping("/upload-notes")
    public String uploadNotes(@RequestParam("file") MultipartFile file,
                              @RequestParam("description") String description,
                              RedirectAttributes redirectAttributes) {
        try {
            File dir = new File(notesUploadDir);
            if (!dir.exists()) dir.mkdirs();

            File dest = new File(notesUploadDir + file.getOriginalFilename());
            file.transferTo(dest);

            Notes note = new Notes();
            note.setFilename(file.getOriginalFilename());
            note.setDescription(description);
            note.setUploadDate(LocalDate.now());
            // If your Notes entity has a 'filePath' field, set it here before saving:
            // note.setFilePath(dest.getAbsolutePath()); // Ensure this is saved to DB if applicable
            notesService.saveNotes(note);

            redirectAttributes.addFlashAttribute("successMessage", "Notes uploaded successfully!");
            return "redirect:/teacher/dashboard";
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Error uploading notes: " + e.getMessage());
            return "redirect:/teacher/dashboard";
        }
    }

    @PostMapping("/update-attendance")
    public String updateAttendance(@RequestParam Long studentId,
                                   @RequestParam int attendance,
                                   RedirectAttributes redirectAttributes) {
        try {
            Student s = studentService.getStudentById(studentId);
            if (s != null) {
                s.setAttendance(attendance);
                studentService.saveStudent(s);
                redirectAttributes.addFlashAttribute("successMessage", "Attendance updated successfully for " + s.getName() + "!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Student with ID " + studentId + " not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating attendance: " + e.getMessage());
        }
        return "redirect:/teacher/dashboard";
    }

    @PostMapping("/delete-note")
    public String deleteNote(@RequestParam("noteId") Long noteId,
                             RedirectAttributes redirectAttributes) throws IOException {
        try {
            notesService.deleteNote(noteId);
            redirectAttributes.addFlashAttribute("successMessage", "Note deleted successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "An unexpected error occurred: " + e.getMessage());
        }
        return "redirect:/teacher/dashboard";
    }

    @PostMapping("/delete-student")
    public String deleteStudent(@RequestParam("studentId") Long studentId,
                                RedirectAttributes redirectAttributes) {
        try {
            studentService.deleteStudent(studentId);
            redirectAttributes.addFlashAttribute("successMessage", "Student deleted successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting student: " + e.getMessage());
        }
        return "redirect:/teacher/dashboard";
    }
}