package com.ex.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ex.sms.entity.*;

public interface TeacherRepository extends JpaRepository<Teacher, Long>
{
	Teacher findByUsername(String username);
}