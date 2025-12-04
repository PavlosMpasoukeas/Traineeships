package com.myy803.traineeship.services.strategies;

import org.springframework.stereotype.Component;

import com.myy803.traineeship.domainmodel.Professor;
import com.myy803.traineeship.domainmodel.TraineeshipPosition;
import com.myy803.traineeship.mappers.ProfessorMapper;
import com.myy803.traineeship.mappers.TraineeshipMapper;

//KLASH POY BRISKEI TON KATALLHLOTERO PROFESOR ME BASH TO WOKRKLOAD TOU(POSES PRAKTIKES EXEI ANALAVEI HDH)

@Component
public class AssignmentBasedOnLoad implements SupervisorAssignmentStrategy {

	ProfessorMapper professorMapper; 
	TraineeshipMapper traineeshipMapper;
	
	public AssignmentBasedOnLoad(ProfessorMapper professorMapper, TraineeshipMapper traineeshipMapper) {
		this.professorMapper = professorMapper;
		this.traineeshipMapper = traineeshipMapper;
	}
	
	@Override
	public void assign(Integer positionId) {
		TraineeshipPosition position = traineeshipMapper.findById(positionId).get();
				
        
        if (professorMapper.findAll().size() > 0 ) {
	        Professor bestProfessor = professorMapper.findAll().get(0);
	        double numberOfSupervised = bestProfessor.getSupervisedPositions().size();
	        	        
			for (Professor p: professorMapper.findAll()) {
				
		        if (p.getSupervisedPositions().size() < numberOfSupervised) {//ΣΥΓΚΡΙΝΩ ΠΡΟΦΕΣΟΡΣ ΜΕΤΑΞΥ ΤΟΥΣ
		        	bestProfessor = p;										//ΚΑΙ ΑΥΤΟΣ ΜΕ ΤΙΣ ΛΙΓΟΤΕΡΕΣ ΠΡΑΚΤΙΚΕΣ
		        	numberOfSupervised = p.getSupervisedPositions().size();//ΑΝΑΤΕΘΕΤΑΙ ΣΑΝ SUPERVISOR 
		        }
			}
			
			// assign best professor result
			position.setSupervisor(bestProfessor);
			traineeshipMapper.save(position);
				
			bestProfessor.getSupervisedPositions().add(position);
			professorMapper.save(bestProfessor);
			

        }

	}

}
