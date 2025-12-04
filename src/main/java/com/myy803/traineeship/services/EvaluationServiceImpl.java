package com.myy803.traineeship.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myy803.traineeship.domainmodel.Evaluation;
import com.myy803.traineeship.mappers.EvaluationMapper;

@Service
public class EvaluationServiceImpl implements EvaluationService{

	@Autowired
	EvaluationMapper evaluationMapper;
	
	@Override
	public void saveEvaluation(Evaluation evaluation) {
		evaluationMapper.save(evaluation);
	}

}
