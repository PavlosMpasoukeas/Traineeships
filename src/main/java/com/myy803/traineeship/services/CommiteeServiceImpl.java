package com.myy803.traineeship.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myy803.traineeship.domainmodel.Student;
import com.myy803.traineeship.domainmodel.TraineeshipPosition;
import com.myy803.traineeship.mappers.ProfessorMapper;
import com.myy803.traineeship.mappers.StudentMapper;
import com.myy803.traineeship.mappers.TraineeshipMapper;
import com.myy803.traineeship.services.strategies.PositionsSearchFactory;
import com.myy803.traineeship.services.strategies.PositionsSearchStrategy;
import com.myy803.traineeship.services.strategies.SupervisorAssignmentFactory;
import com.myy803.traineeship.services.strategies.SupervisorAssignmentStrategy;

@Service
public class CommiteeServiceImpl implements CommiteeService {

	@Autowired
	StudentMapper studentMapper;
	
	@Autowired
	TraineeshipMapper traineeshipMapper;
	
	@Autowired
	ProfessorMapper professorMapper;
	
	@Autowired
	PositionsSearchFactory searchFactory;
	
	@Autowired
	SupervisorAssignmentFactory assignmentFactory;
	
	@Override
	public List<TraineeshipPosition> retrievePositionsForApplicant(String applicantUsername, String strategy) {
		
		PositionsSearchStrategy searchStrategy = searchFactory.create(strategy);
		
		return searchStrategy.search(applicantUsername);
	}

	@Override
	public List<Student> retrieveTraineeshipApplications() { 
		List<Student> students = new ArrayList<Student>();
		
		for (Student student: studentMapper.findAll()) {
			if (student.isLookingForTraineeship() && student.getAssignedPosition() == null) {
				students.add(student);
			}
		}
		
		return students;
	}

	@Override
	public void assignPosition(Integer positionId, String studentUsername) {// methodos gia na anathesw thesi
		Student student = studentMapper.findByUsername(studentUsername).get();// to student einai domain model klash
		TraineeshipPosition position = traineeshipMapper.findById(positionId).get();//h position einai domainmodel
		
		student.setAssignedPosition(position);
		studentMapper.save(student);
		
		position.setAssigned(true); 
		position.setStudent(student);
		traineeshipMapper.save(position);
	}

	@Override
	public void assignSupervisor(Integer positionId, String strategy) {
		
		SupervisorAssignmentStrategy assignmentStrategy = assignmentFactory.create(strategy);
		
		assignmentStrategy.assign(positionId);

	}

	@Override
	public List<TraineeshipPosition> listAssignedTraineeships() {
		return traineeshipMapper.findAllByIsAssigned(true);
	}

	@Override
	public void completeAssignedTraineeships(Integer positionId, boolean grade) {
		TraineeshipPosition position = traineeshipMapper.findById(positionId).get();
		position.setPassFailGrade(grade);
		traineeshipMapper.save(position);
	}


}
