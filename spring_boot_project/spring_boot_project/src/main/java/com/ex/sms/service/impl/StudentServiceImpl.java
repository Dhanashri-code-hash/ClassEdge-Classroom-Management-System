package com.ex.sms.service.impl;

import com.ex.sms.entity.Student;
import com.ex.sms.repository.StudentRepository;
import com.ex.sms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student login(String email, String password) {
        // IMPORTANT: In a real application, implement proper password hashing (e.g., BCrypt)
        // Never store or compare plain text passwords.
        Student student = studentRepository.findByEmail(email); // Assuming findByEmail exists
        if (student != null && student.getPassword().equals(password)) { // DANGER: Plain text password check!
            return student;
        }
        return null; // Login failed
    }

    @Override
    @Transactional // Ensures the entire operation (including cascading deletions) is atomic
    public void deleteStudent(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new IllegalArgumentException("Student with ID " + studentId + " not found.");
        }

        // If you've configured CascadeType.ALL and orphanRemoval=true on Student's @OneToMany relationship
        // with Assignments, then deleting the student will automatically delete their assignments.
        // Otherwise, you would need to manually delete related assignments here.

        studentRepository.deleteById(studentId);
        System.out.println("Deleted student record from DB with ID: " + studentId);
    }
}