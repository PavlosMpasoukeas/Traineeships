package com.myy803.traineeship.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.myy803.traineeship.domainmodel.Student;
import com.myy803.traineeship.domainmodel.TraineeshipPosition;
import com.myy803.traineeship.mappers.StudentMapper;
import com.myy803.traineeship.mappers.TraineeshipMapper;

@Service
public class TraineeshipServiceImpl implements TraineeshipService {

	@Autowired
	TraineeshipMapper traineeshipMapper;
	
	@Autowired
	StudentMapper studentMapper;
	
	@Override
	public void save(TraineeshipPosition position) {
		traineeshipMapper.save(position);
	}

	@Override
	public TraineeshipPosition retrievePosition(String username) { //epistrefei θεση πρακτικης στον φοιτητη
		Student student = studentMapper.findByUsername(username).get();
		return student.getAssignedPosition();
	}

	@Override
	public void deletePosition(Integer id) {//diagrafei thesi(ginetai otan pathsw to koubi delete free positions
		traineeshipMapper.deleteById(id);//to diagrafei apth Β.Δ
	}

	@Override
	public void saveLogbook(String logbook) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String username = authentication.getName();
	    
		TraineeshipPosition position = studentMapper.findByUsername(username).get().getAssignedPosition();//PERNEI THN ASSIGNED THESI PRAKTIKHS POUXEI O FOITHTHS
		position.setStudentLogbook(logbook); //GT AN DEN EXEI GINEI ASSIGNED DEN MPORW NA KANW SAVE TO LOGBOOK WS FOITHTHS
		traineeshipMapper.save(position);
	}

	@SuppressWarnings("deprecation")
	@Override
	public TraineeshipPosition retrievePositionById(int id) {
		return traineeshipMapper.getById(id);
	}

}
