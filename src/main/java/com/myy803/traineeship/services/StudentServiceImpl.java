package com.myy803.traineeship.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myy803.traineeship.domainmodel.Student;
import com.myy803.traineeship.domainmodel.TraineeshipPosition;
import com.myy803.traineeship.mappers.StudentMapper;
import com.myy803.traineeship.mappers.TraineeshipMapper;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	StudentMapper studentMapper;
	
	@Autowired
	TraineeshipMapper traineeshipMapper;
	
	@Override
	public void saveProfile(Student student) {
		studentMapper.save(student);
	}

	@Override
	public Student retrieveProfile(String studentUsername) {
		Optional<Student> student = studentMapper.findByUsername(studentUsername);
		
		if (student.isPresent()) {//an brhke ton foithth apth Β.Δ.(μεσω του μαπερ απο πανω) επεστρεψε το προφιλ του
			return student.get();
		}
		else {
			Student newStudent = new Student();//αν δεν υπαρχει το προφιλ επιστρεφει εναν νεο φοιτητη
			newStudent.setUsername(studentUsername);
			return newStudent;
		}
	}

	@Override
	public void saveLogbook(TraineeshipPosition position) {
		traineeshipMapper.save(position);
	}

	@Override
	public boolean isStudentPresent(String username) {
		Optional<Student> storedStudent = studentMapper.findByUsername(username);
		return storedStudent.isPresent();
	}

}
