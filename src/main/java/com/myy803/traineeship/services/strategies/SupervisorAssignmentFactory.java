package com.myy803.traineeship.services.strategies;

//STO FACTORY KALW THN STRATIGIKH WS ORISMA KAI ANALOGWS KANW TIS ADISTOIXES LEITOURGIES

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myy803.traineeship.mappers.ProfessorMapper;
import com.myy803.traineeship.mappers.TraineeshipMapper;

@Component
public class SupervisorAssignmentFactory {

	@Autowired
	ProfessorMapper professorMapper; 
	
	@Autowired
	TraineeshipMapper traineeshipMapper;
	
	public SupervisorAssignmentStrategy create(String strategy) {
		//an thelw me bash ta interests epistrefw thn nea klash AssignmentBasedOnInterests
		if (strategy.equals("interests")) {
			return new AssignmentBasedOnInterests(professorMapper, traineeshipMapper);//KALW TON CONSTRUCTOR ME ORISMATA TOYS MAPPERS
		}
		else { //alliws to idio gia thn klash me ta workloads
			return new AssignmentBasedOnLoad(professorMapper, traineeshipMapper);//KALW TON CONSTRUCTOR ME ORISMATA TOYS MAPPERS
		}
	}
}
