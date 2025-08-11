package com.ex.sms.service;

import com.ex.sms.entity.*;
import java.util.*;

public interface StudentService
{
    Student saveStudent(Student student);
    Student getStudentById(Long id);

    List<Student> getAllStudents();
    Student login(String email, String password);
    void deleteStudent(Long studentId); // <--- NEW METHOD
}