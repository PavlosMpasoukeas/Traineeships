package com.myy803.traineeship.services;

import java.util.List;

import com.myy803.traineeship.domainmodel.Evaluation;
import com.myy803.traineeship.domainmodel.Professor;
import com.myy803.traineeship.domainmodel.TraineeshipPosition;

public interface ProfessorService {
	Professor retrieveProfile(String username);
	boolean isProfessorPresent(String username);
	void saveProfile(Professor professor);
	List<TraineeshipPosition> retrieveAssignedPositions();
	void evaluateAssignedPosition(Integer positionId);
	void saveEvaluation(Integer positionId, Evaluation evaluation);
	List<Professor> findAll();
}
