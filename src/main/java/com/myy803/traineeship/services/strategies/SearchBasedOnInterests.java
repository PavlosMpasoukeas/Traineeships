package com.myy803.traineeship.services.strategies;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;

import com.myy803.traineeship.domainmodel.TraineeshipPosition;
import com.myy803.traineeship.mappers.StudentMapper;
import com.myy803.traineeship.mappers.TraineeshipMapper;

@Component
public class SearchBasedOnInterests implements PositionsSearchStrategy {

	TraineeshipMapper traineeshipMapper;
	StudentMapper studentMapper;
	
	public SearchBasedOnInterests(TraineeshipMapper traineeshipMapper, StudentMapper studentMapper) {
		this.traineeshipMapper = traineeshipMapper;
		this.studentMapper = studentMapper;
	}
	
	@Override
	public List<TraineeshipPosition> search(String applicantUsername) {
		String interestsString = studentMapper.findByUsername(applicantUsername).get().getInterests();
		String skillsString = studentMapper.findByUsername(applicantUsername).get().getSkills();
		Set<String> interests = new HashSet<>();
	    for (String s : interestsString.split(",")) { //PERNOUME TA INTERESTS TOU FOITHTH
	        interests.add(s.trim());
	    }
	    Set<String> skills = new HashSet<>();
	    for (String s : skillsString.split(",")) { //PERNOUME TA SKILLS TOU FOITHTH
	    	skills.add(s.trim());
	    }

	    List<TraineeshipPosition> results = new ArrayList<>();		
	    
	    for (TraineeshipPosition p : traineeshipMapper.findAll()) {
	        String topicsString = p.getTopics();
	        Set<String> topics = new HashSet<>();
	        for (String t : topicsString.split(",")) { //PERNOUME TA TOPICS THS PRAKTIKHS
	            topics.add(t.trim());
	        }
	        String pSkillsString = p.getSkills();
	        Set<String> pSkills = new HashSet<>();
	        for (String t : pSkillsString.split(",")) { //PERNOUME SKILLS PRAKTIKHS
	        	pSkills.add(t.trim());
	        }

	        Set<String> intersection = new HashSet<>(interests); //to intersection periexei ola ta interests
	        intersection.retainAll(topics);//meta tou leme mesw ths retainAll tha krathsei mono ta interests pou iparhoun kai sta topics

	        Set<String> union = new HashSet<>(interests);//adistoixa sto union exoume pali ola ta interests
	        union.addAll(topics);//kai tou leme mesw ths unionAll prosthese kai ola ta topics(oxi diplotipa)
	        
	        double jaccard = 0; 
	        if (!union.isEmpty()) {
	            jaccard = (double) intersection.size() / union.size();
	        }
	        
	        Set<String> pIntersection = new HashSet<>(skills); //TO SET PERNEI MESA OLA TA SKILLS TOU FOITHTH
	        pIntersection.retainAll(pSkills); //MESW TOU RETAINALL ME ORISMA TA SKILLS THS PRAKTIKHS PERNEI THN TOMH TOYS

	        Set<String> pUnion = new HashSet<>(skills);
	        pUnion.addAll(pSkills);  //MESW TOU ADDALL THA PARW THN ENWSH
	        
	        double pJaccard = 0;  //THA EXW 2o JACCARD SIMULARITY GIANA MATCHARW TA SKILLS THS PRAKTIKHS MAFTA TOU STUDENT
	        if (!pUnion.isEmpty()) {
	            pJaccard = (double) pIntersection.size() / pUnion.size();
	        }

	        if (!p.isAssigned() && jaccard >= 0.5 && pJaccard >= 0.5) { //DEN THELW NA EINAI ASSIGNED GIATI EIPAME OTI EXW ONETOONE SXESH ME TON FOITHTH KAI THN THESI PRAKTIKHS
	            results.add(p);									//ARA AN EINAI KATEILHMENH DEN BOREI NA BEI ALLOS FOITHTHS
	        }
	    }
	    
	    return results; //EPISTREFW THN LISTA ME OLES TIS THESEISPRAKTIKHS POU EINAI KATALLHLES GIANA GINOUN ASSIGNED

	}
	
	
}
