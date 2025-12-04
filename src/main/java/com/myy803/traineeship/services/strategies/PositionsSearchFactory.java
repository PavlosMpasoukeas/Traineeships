package com.myy803.traineeship.services.strategies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myy803.traineeship.mappers.CompanyMapper;
import com.myy803.traineeship.mappers.StudentMapper;
import com.myy803.traineeship.mappers.TraineeshipMapper;

@Component
public class PositionsSearchFactory {

	@Autowired
	TraineeshipMapper traineeshipMapper;
	
	@Autowired
    StudentMapper studentMapper;
	
	
	public PositionsSearchStrategy create(String strategy) {
		
		if (strategy.equals("interests")) {
			return new SearchBasedOnInterests(traineeshipMapper, studentMapper); //KALW TON CONSTRUCTOR ME ORISMATA TOUS MAPPERS
		}
		else if (strategy.equals("location")) {
			return new SearchBasedOnLocation(traineeshipMapper, studentMapper); //KALW TON CONSTRUCTOR ME ORISMATA TOUS MAPPERS
		}
		else {
			return new SearchBasedOnInterestsAndLocation(traineeshipMapper, studentMapper); //KALW TON CONSTRUCTOR ME ORISMATA TOUS MAPPERS
		}

	}
}
