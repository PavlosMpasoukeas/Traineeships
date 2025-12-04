package com.myy803.traineeship.mappers;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myy803.traineeship.domainmodel.Company;
import com.myy803.traineeship.domainmodel.TraineeshipPosition;

public interface TraineeshipMapper extends JpaRepository<TraineeshipPosition, Integer>{

	List<TraineeshipPosition> findAllByCompany(Optional<Company> optional);
	List<TraineeshipPosition> findAllByIsAssigned(boolean isAssigned);
}
