package com.myy803.traineeship.services;

import org.springframework.stereotype.Service;

import com.myy803.traineeship.domainmodel.TraineeshipPosition;

@Service
public interface TraineeshipService {

	void save(TraineeshipPosition position);
	TraineeshipPosition retrievePosition(String username);
	void deletePosition(Integer id);
	void saveLogbook(String logbook);
	TraineeshipPosition retrievePositionById(int id);
}
