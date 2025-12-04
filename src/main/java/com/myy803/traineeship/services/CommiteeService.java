package com.myy803.traineeship.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myy803.traineeship.domainmodel.Student;
import com.myy803.traineeship.domainmodel.TraineeshipPosition;

@Service
public interface CommiteeService {
	public List<TraineeshipPosition> retrievePositionsForApplicant(String applicantUsername, String strategy);
	public List<Student> retrieveTraineeshipApplications();
	public void assignPosition(Integer positionId, String studentUsername);
	public void assignSupervisor(Integer positionId, String strategy);
	public List<TraineeshipPosition> listAssignedTraineeships();
	public void completeAssignedTraineeships(Integer positionId, boolean grade);
}
