package com.myy803.traineeship.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myy803.traineeship.domainmodel.Evaluation;
import com.myy803.traineeship.domainmodel.EvaluationType;
import com.myy803.traineeship.domainmodel.Professor;
import com.myy803.traineeship.services.ProfessorService;

@Controller
public class ProfessorController {
	
	@Autowired
	ProfessorService professorService;
	
	@RequestMapping("/professor/dashboard")
    public String getStudentDashboard(Model model){

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String currentUsername = authentication.getName();
	    
		if (professorService.isProfessorPresent(currentUsername)) { //αν υπαρχει ηδη καθηγητης 
			model.addAttribute("student", professorService.retrieveProfile(currentUsername)); //παιρνουμε το προφιλ του
			return "professor/dashboard";
		}
		else {
			return "redirect:/professor/profile";
		}
    }
	
	@RequestMapping("/professor/profile") //συμπληρωνω προφιλ καθηγητη
	public String getProfile(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String currentUsername = authentication.getName();
	    
	    model.addAttribute("professor", professorService.retrieveProfile(currentUsername));
		
		return "/professor/profile";
	}
	
	@RequestMapping("/professor/profile/save") //αφου το συμπληρωσα το αποθηκευω
	public String saveeProfile(@ModelAttribute("professor") Professor professor) { //ksanapernw to adikeimeno professor apo pio panw afou eftiaxa to profil
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String currentUsername = authentication.getName();
	    
	    professor.setUsername(currentUsername); //ξανα θετω το username στον καθηγητη
		professorService.saveProfile(professor);
		
		return "redirect:/professor/dashboard";
	}
	
	@RequestMapping("/professor/positions")
	public String getPositions(Model model) { //ΤΗΑ ΕΠΙΣΤΡΕΨΕΙ ΤΙΣ ΘΕΣΕΙΣ ΠΡΑΚΤΙΚΗΣ ΣΤΙΣ ΟΠΟΙΕΣ ΕΙΝΑΙ SUPERVISED Ο ΚΑΘΗΓΗΤΗΣ
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String currentUsername = authentication.getName();
	    
	    model.addAttribute("positions", professorService.retrieveProfile(currentUsername).getSupervisedPositions());//bres to profil tou professor
																		///////-------->										//dwstw sthn html
		return "/professor/positions";
	}
	
	@RequestMapping("/professor/positions/{id}/evaluation")
	public String getEvaluation(Model model, @PathVariable int id) { //παιρνει το id της θεσης πρακτικης απτο URL
		model.addAttribute("posId", id); //το στελνει στην html 
		model.addAttribute("evaluation", new Evaluation()); //mazi kai to evaluation
		return "/professor/evaluation";
	}
	
	@RequestMapping("/professor/positions/{id}/evaluation/save")
	public String saveEvaluation(@ModelAttribute Evaluation evaluation, @PathVariable int id) {
		evaluation.setEvaluationType(EvaluationType.PROFESSOR); //θετει ποιανου evaluation ειναι αυτο
		professorService.saveEvaluation(id, evaluation); //αποθηκευει την βαθμολογηση του καθηγητη και πηγαινει πισω στο dashboard
		return "redirect:/professor/dashboard";
	}

}
