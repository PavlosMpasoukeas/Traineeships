package com.myy803.traineeship.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myy803.traineeship.domainmodel.EvaluationType;
import com.myy803.traineeship.domainmodel.TraineeshipPosition;
import com.myy803.traineeship.services.CommiteeService;
import com.myy803.traineeship.services.ProfessorService;
import com.myy803.traineeship.services.TraineeshipService;

@Controller
public class CommiteeController {

	@Autowired
	CommiteeService commiteeService;
	
	@Autowired
	ProfessorService professorService;
	
	@Autowired
	TraineeshipService traineeshipService;
	
	@RequestMapping("/commitee/dashboard")
	public String getDashboard() {
		
		return "commitee/dashboard";
	}
	
	@RequestMapping("/commitee/students")
	public String getAvailableStudents(Model model) {
		
		model.addAttribute("students", commiteeService.retrieveTraineeshipApplications());
		
		return "commitee/students";
	}
	
	@RequestMapping("/commitee/in-progress-traineeships")
	public String getInProgressTraineeships(Model model) {
		
		model.addAttribute("positions", commiteeService.listAssignedTraineeships());
		
		return "commitee/assigned-positions";
	}
		
	@RequestMapping("/commitee/assign-professor/{id}/interests")
	public String assignProfessorBasedOnInterests(@PathVariable int id) {
		commiteeService.assignSupervisor(id, "interests");
		
		return "redirect:/commitee/dashboard";
	}
	
	@RequestMapping("/commitee/assign-professor/{id}/workload")
	public String assignProfessorBasedOnWorkload(@PathVariable int id) {
		commiteeService.assignSupervisor(id, "workload");
				
		
		return "redirect:/commitee/dashboard";
	}
	
	@RequestMapping("/commitee/students/{username}/interests") //EDW EPISTREPSAME TIS THESEIS POU  EINAI KATALLHLES GIA ASSIGN ME BASH TA INTERESTS
	public String getTraineeshipsByInterests(@PathVariable String username, Model model) {
		
		model.addAttribute("positions", commiteeService.retrievePositionsForApplicant(username, "interests"));
		
		return "commitee/results"; 
	}
	
	@RequestMapping("/commitee/students/{username}/location")//EDW EPISTREPSAME TIS THESEIS POU  EINAI KATALLHLES GIA ASSIGN ME BASH To LOCATION
	public String getTraineeshipsByLocation(@PathVariable String username, Model model) {
		
		model.addAttribute("positions", commiteeService.retrievePositionsForApplicant(username, "location"));
		
		return "commitee/results"; 
	}
	
	@RequestMapping("/commitee/students/{username}/both")//EDW EPISTREPSAME TIS THESEIS POU  EINAI KATALLHLES GIA ASSIGN ME BASH KAI TA 2
	public String getTraineeshipsByInterestsAndLocation(@PathVariable String username, Model model) {
		
		model.addAttribute("positions", commiteeService.retrievePositionsForApplicant(username, "both"));
		
		return "commitee/results"; 
	}
	
	@RequestMapping("/commitee/students/{username}/assign/{id}")//EDW EINAI POU KANOUME ODWS ASSIGN TON FOITHTH STHN PRAKTIKH
	public String assignStudent(@PathVariable String username, @PathVariable int id) {
		
		commiteeService.assignPosition(id, username);
		
		return "redirect:/commitee/dashboard";
	}
	

	@RequestMapping("/commitee/positions/{id}/final") //edw exei pathsei koubi final evaluation
	public String getFinalEvaluation(@PathVariable int id, Model model) {
		TraineeshipPosition position = traineeshipService.retrievePositionById(id);  //mesw tou id apthn URL epistrefei thn thesi praktikhs pou briskomaste
		
		EvaluationType type1 = position.getEvaluations().get(0).getEvaluationType(); //pernei to 1o apta 2 type(professor h company) tihaia epilexame to prwto
		if (type1.equals(EvaluationType.COMPANY)) {
			model.addAttribute("compEvaluation", position.getEvaluations().get(0)); //an einai company to 1o  to bazw sthn 1h thesh ths listas
			model.addAttribute("profEvaluation", position.getEvaluations().get(1)); //to professor sthn 2h thesi
		}
		else {
			model.addAttribute("compEvaluation", position.getEvaluations().get(1)); //alliws to anapodo
			model.addAttribute("profEvaluation", position.getEvaluations().get(0));
		}
		
		model.addAttribute("id", id);
				
		return "commitee/final";
	}
	
	@RequestMapping("/commitee/positions/{id}/final/{grade}")
	public String saveFinalGrade(@PathVariable int id, @PathVariable boolean grade) {
		commiteeService.completeAssignedTraineeships(id, grade);
		
		return "redirect:/commitee/dashboard";
	}
}
