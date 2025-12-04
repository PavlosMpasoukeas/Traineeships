package com.myy803.traineeship.services.strategies;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.myy803.traineeship.domainmodel.Professor;
import com.myy803.traineeship.domainmodel.TraineeshipPosition;
import com.myy803.traineeship.mappers.ProfessorMapper;
import com.myy803.traineeship.mappers.TraineeshipMapper;

//ΣΑΦΤΗ ΤΗ ΚΛΑΣΗ ΨΑΧΝΩ ΝΑ ΒΡΩ ΤΟΝ ΚΑΤΑΛΛΗΛΟΤΕΡΟ ΚΑΘΗΓΗΤΗ ΓΙΑ SUPERVISOR ΜΕ ΒΑΣΗ ΤΑ ΕΝΔΙΑΦΕΡΟΝΤΑ ΤΟΥ

@Component
public class AssignmentBasedOnInterests implements SupervisorAssignmentStrategy {//PRIN TO KANW ASSIGN BRISKW TON KATALLHLOTERO KATHIGHTH
																			//ME BASH TA ENDIAFERONTA TOU
	
	TraineeshipMapper traineeshipMapper; //ΘΑ ΠΡΕΠΕΙ ΝΑ ΜΑΤΣΑΡΩ ΤΑ ΕΝΔΙΑΦΕΡΟΝΤΑΙ ΤΟΥ ΚΑΘΗΓΗΤΗ ΜΕ ΤΑ ΤΟΠΙΚΣ ΤΗΣ ΠΡΑΚΤΙΚΗ 
	
	ProfessorMapper professorMapper; 
	
	public AssignmentBasedOnInterests(ProfessorMapper professorMapper, TraineeshipMapper traineeshipMapper) {
		this.professorMapper = professorMapper;
		this.traineeshipMapper = traineeshipMapper;
	}

	@Override
	public void assign(Integer positionId) {
		TraineeshipPosition position = traineeshipMapper.findById(positionId).get();
		String topicsString = position.getTopics();
        Set<String> topics = new HashSet<>();
        for (String t : topicsString.split(",")) {
            topics.add(t.trim());
        }
		
        
        if (professorMapper.findAll().size() > 0 ) { //ΚΑΝΩ ΕΛΕΓΧΟ ΑΡΧΙΚΑ ΟΤΙ ΥΠΑΡΧΟΥΝ ΚΑΘΗΓΗΤΕΣ
	        Professor bestProfessor = professorMapper.findAll().get(0);
	        double bestSimilarity = 0;
	        
			for (Professor p: professorMapper.findAll()) {
				String interestsString = p.getInterests();
		        Set<String> interests = new HashSet<>();
		        for (String t : interestsString.split(",")) { //ΑΦΟΥ ΤΑ ΕΝΔΙΑΦΕΡΟΝΤΑ ΧΩΡΙΖΩΝΤΑΙ ΜΕ ΚΟΜΜΑ
		            interests.add(t.trim());
		        }
		        
		        Set<String> intersection = new HashSet<>(topics);
		        intersection.retainAll(interests);
		        Set<String> union = new HashSet<>(topics);
		        union.addAll(interests);
		        
		        
		        double jaccard = 0; 
		        if (!union.isEmpty()) { //ELEGXOUME GIANA MHN EXOUME META PARONOMASTH 0 GT AFTO DEN GINETAI
		            jaccard = (double) intersection.size() / union.size(); //edw!!!
		        }
	
		        if (jaccard >= 0.5 && jaccard >= bestSimilarity) {  
		        	bestProfessor = p;
		        	bestSimilarity = jaccard;
		        }
			}
			
			//EDW KANW TO ASSIGN  
			// assign best professor result
			if (bestSimilarity > 0) { //IPARHEI PERIPTWSH NA MH BEI SE KANENA IF APO PANW ARA NA PARAMEINEI 0(dld na mhn einai katallhlos gia supervisor)  
				
				position.setSupervisor(bestProfessor); //bazw ws supervison ton proffesor
				traineeshipMapper.save(position);   //edw tha gemisei kai thn sthlh tou professor_username(prin ginei assign htan null) 
				
				bestProfessor.getSupervisedPositions().add(position); //pernw tis supervised thesei wste na tis xrisimopoihsw sto allo strategy pou thelei workload
				professorMapper.save(bestProfessor);					//kai na xerw poios exei to ligotero workload
			}

        }
		
	}

}
