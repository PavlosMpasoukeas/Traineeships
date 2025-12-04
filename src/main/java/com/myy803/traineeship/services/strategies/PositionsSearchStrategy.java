package com.myy803.traineeship.services.strategies;

import java.util.List;

import com.myy803.traineeship.domainmodel.TraineeshipPosition;

public interface PositionsSearchStrategy {

	List<TraineeshipPosition> search(String applicantUsername);
}
