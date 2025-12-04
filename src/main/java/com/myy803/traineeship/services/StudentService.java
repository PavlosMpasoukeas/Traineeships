package com.myy803.traineeship.services;

import org.springframework.stereotype.Service;

import com.myy803.traineeship.domainmodel.Student;
import com.myy803.traineeship.domainmodel.TraineeshipPosition;

@Service
public interface StudentService {

	void saveProfile(Student student);
	Student retrieveProfile(String studentUsername);
	void saveLogbook(TraineeshipPosition position);
	public boolean isStudentPresent(String username);
}
