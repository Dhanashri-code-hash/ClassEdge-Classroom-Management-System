package com.ex.sms.service.impl; // Create this package if it doesn't exist

import com.ex.sms.entity.Assignment;
import com.ex.sms.repository.AssignmentRepository;
import com.ex.sms.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Override
    public Assignment saveAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    @Override
    public List<Assignment> getAssignmentsByStudentId(Long studentId) {
        return assignmentRepository.findByStudentId(studentId);
    }

    @Override
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    // You might need this if you manually delete assignments
    // @Transactional
    // public void deleteAssignmentsByStudentId(Long studentId) {
    //     List<Assignment> assignments = assignmentRepository.findByStudentId(studentId);
    //     assignmentRepository.deleteAll(assignments); // Deletes all assignments in the list
    // }
}