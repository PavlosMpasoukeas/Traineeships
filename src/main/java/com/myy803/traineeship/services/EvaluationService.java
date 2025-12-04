package com.myy803.traineeship.services;

import org.springframework.stereotype.Service;

import com.myy803.traineeship.domainmodel.Evaluation;

@Service
public interface EvaluationService {
	void saveEvaluation(Evaluation evaluation);
}
