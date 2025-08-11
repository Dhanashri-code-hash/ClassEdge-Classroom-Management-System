package com.ex.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ex.sms.entity.*;

public interface NotesRepository extends JpaRepository<Notes, Long>
{
	
}
