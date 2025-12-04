package com.myy803.traineeship.mappers;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myy803.traineeship.domainmodel.Student;

public interface StudentMapper extends JpaRepository<Student, String>{
	Optional<Student> findByUsername(String username);
}
