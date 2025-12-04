package com.myy803.traineeship.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myy803.traineeship.domainmodel.Company;
import com.myy803.traineeship.domainmodel.Evaluation;
import com.myy803.traineeship.domainmodel.TraineeshipPosition;
import com.myy803.traineeship.mappers.CompanyMapper;
import com.myy803.traineeship.mappers.EvaluationMapper;
import com.myy803.traineeship.mappers.TraineeshipMapper;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyMapper companyMapper;
	
	@Autowired
	private TraineeshipMapper traineeshipMapper;
	@Autowired
	EvaluationMapper evaluationMapper;
	
	@Override
	public boolean isCompanyPresent(String username) {
		Optional<Company> storedCompany = companyMapper.findByUsername(username);
		return storedCompany.isPresent(); //H isPresent ειναι ετοιμη μεθοδος της optional
	}

	@Override
	public void saveProfile(Company company) {
		companyMapper.save(company);
	}

	@Override
	public Company retrieveProfile(String username) {
		return companyMapper.findByUsername(username).get(); //H get() ειναι ετοιμη μεθοδος της Optional
	}

	@Override
	public List<TraineeshipPosition> retrieveAvailablePositions(String username) {//με αυτη τη μεθοδο παιρνουμε ολα τα positions
		List<TraineeshipPosition> positions = new ArrayList<TraineeshipPosition>();//που δεν ειναι assigned
		Optional<Company> company = companyMapper.findByUsername(username);
		
		for (TraineeshipPosition position: traineeshipMapper.findAllByCompany(company)) { //περνουμαι ολες τις πρακτικες με βαση την εταιρεια
			if (!position.isAssigned()) { //αν η θεση δεν ειναι κατειλημενη προσθεσε την
				positions.add(position);
			}
		}
		
		return positions;
	}

	@Override
	public void addPosition(String username, TraineeshipPosition position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<TraineeshipPosition> retrieveAssignedPositions(String username) {//mayth th methodo pernoume oles tis assigned positions
		List<TraineeshipPosition> positions = new ArrayList<TraineeshipPosition>();
		Optional<Company> company = companyMapper.findByUsername(username);
		
		for (TraineeshipPosition position: traineeshipMapper.findAllByCompany(company)) {//ψαξε και βρες τισ πρακτικεσ απτην εταιρεια
			if (position.isAssigned()) { //αν ειναι κατειλημενη προσθεση την στην λιστα
				positions.add(position);
			}
		}
		
		return positions;//επεστρεψε την λιστα με τις κατειλημενες πρακτικεσ
	}

	@Override
	public void evaluateAssignedPosition(Integer positionId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveEvaluation(Integer positionId, Evaluation evaluation) {
		Optional<TraineeshipPosition> position = traineeshipMapper.findById(positionId);
		
		evaluationMapper.save(evaluation);
		
		position.get().getEvaluations().add(evaluation);
		
		traineeshipMapper.save(position.get());
		
		
	}

}
