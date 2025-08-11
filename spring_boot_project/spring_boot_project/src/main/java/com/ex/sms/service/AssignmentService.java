package com.ex.sms.service;

import com.ex.sms.entity.*;
import java.util.*;

public interface AssignmentService 
{
	Assignment saveAssignment(Assignment assignment);
	List<Assignment> getAssignmentsByStudentId(Long studentId);
	List<Assignment> getAllAssignments();
}
