package com.myy803.traineeship.mappers;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myy803.traineeship.domainmodel.Professor;

public interface ProfessorMapper extends JpaRepository<Professor, String>{
	Optional<Professor> findByUsername(String username);
	
}
