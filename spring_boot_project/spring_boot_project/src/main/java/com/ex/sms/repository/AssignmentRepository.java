package com.ex.sms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ex.sms.entity.Assignment;

//com.ex.sms.repository.AssignmentRepository.java
import org.springframework.data.jpa.repository.EntityGraph; // Import this

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
 List<Assignment> findByStudentId(Long studentId);

 @Override
 @EntityGraph(attributePaths = "student") // <--- Add this
 List<Assignment> findAll();
}