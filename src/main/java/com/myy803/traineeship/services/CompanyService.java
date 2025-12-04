package com.myy803.traineeship.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myy803.traineeship.domainmodel.Company;
import com.myy803.traineeship.domainmodel.Evaluation;
import com.myy803.traineeship.domainmodel.TraineeshipPosition;

@Service
public interface CompanyService {
	public Company retrieveProfile(String username);
	public void saveProfile(Company company);
	public boolean isCompanyPresent(String username);
	public List<TraineeshipPosition> retrieveAvailablePositions(String username);
	public void addPosition(String username, TraineeshipPosition position);
	public List<TraineeshipPosition> retrieveAssignedPositions(String username);
	public void evaluateAssignedPosition(Integer positionId);
	public void saveEvaluation(Integer positionId, Evaluation evaluation);
}
