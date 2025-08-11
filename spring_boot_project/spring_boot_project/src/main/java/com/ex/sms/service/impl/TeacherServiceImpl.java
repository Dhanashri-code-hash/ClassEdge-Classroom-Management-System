package com.ex.sms.service.impl;

import com.ex.sms.entity.Teacher;
import com.ex.sms.repository.TeacherRepository;
import com.ex.sms.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public Teacher login(String username, String password) {
        // IMPORTANT: In a real application, implement proper password hashing (e.g., BCrypt)
        // Never store or compare plain text passwords.
        Teacher teacher = teacherRepository.findByUsername(username); // Assuming findByUsername exists
        if (teacher != null && teacher.getPassword().equals(password)) { // DANGER: Plain text password check!
            return teacher;
        }
        return null; // Login failed
    }
}