package com.myy803.traineeship.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myy803.traineeship.domainmodel.Company;
import com.myy803.traineeship.domainmodel.Evaluation;
import com.myy803.traineeship.domainmodel.EvaluationType;
import com.myy803.traineeship.domainmodel.Student;
import com.myy803.traineeship.domainmodel.TraineeshipPosition;
import com.myy803.traineeship.domainmodel.User;
import com.myy803.traineeship.services.CompanyService;
import com.myy803.traineeship.services.EvaluationService;
import com.myy803.traineeship.services.TraineeshipService;
import com.myy803.traineeship.services.UserService;

@Controller
public class CompanyController {

	@Autowired
	CompanyService companyService; 
	
	@Autowired
	UserService userService;
	
	@Autowired
	TraineeshipService traineeshipService;
	
	@Autowired
	EvaluationService evaluationService;
	
	@RequestMapping("/company/dashboard") //afou exw  dimourghsei thn etaireia epistrefw sto dashboard
	public String getCompanyDashboard() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String currentUsername = authentication.getName();//pernw to onoma me to opoio exw sindethei
	    
		if (companyService.isCompanyPresent(currentUsername)) {//afou exw  dimourghsei thn etaireia epistrefw sto dashboard
			return "company/dashboard";
		}
		else {
			return "redirect:/company/create";//
		}
	}
	
	@RequestMapping("/company/create") //edw dhmiourgw thn etaireia einai san ton sign up tou user
	public String getCreateForm(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String currentUsername = authentication.getName(); //pernw to onoma me to opoio exw sindethei
		Company company = new Company();
		company.setUsername(currentUsername);//θετω στην εταιρεια που δημιουργησα το username
		
		model.addAttribute("company", company);//an den iparhei hdh h etaireia thn dhmiourgw
		return "company/create";
	}
	
	@PostMapping("/company/save")//afou thn dhmiourghsa paw na thn apothikefsw
	public String createCompany(@ModelAttribute("company") Company company) {//pernei pisw to adikeimeno company pou perasame prin sthn apo panw methodo
		companyService.saveProfile(company);//mesa apthn klash companyServiceImpl thn apothikevw
											//h companyService adistoixa kalei ton companyMapper giana to perasei kai sthn Β.Δ.
		return "redirect:/company/dashboard";
	}
	
	@RequestMapping("/company/free-positions")////MOU EPISTREFEI ME TO POU PATHSW TO KOUBI FREE POSITIONS TIS ELEFTHERES THESEIS!!!!
	public String getFreePositionsList(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String currentUsername = authentication.getName();
	    model.addAttribute("positions", companyService.retrieveAvailablePositions(currentUsername));//h methodos retrieveAvailablePositions einai apthn companyService
																						//tha dwsw sthn html tis free positions
		return "company/free-positions";
	}
	
	@RequestMapping("/company/assigned-positions")////MOU EPISTREFEI ME TO POY PATHSW TO KOUBI ASSIGNED POSITIONS kateilhmenes theseis praktikhs
	public String getAssignedPositionsList(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String currentUsername = authentication.getName();
	    model.addAttribute("positions", companyService.retrieveAssignedPositions(currentUsername));//mesw ths methodou tou service pairnw tis kateilhmenes theseis praktikhs
																								//kai tis dinw sthn assignes-positions.html
		return "company/assigned-positions";
	}
	
	@RequestMapping("/company/new-position") //δημιουργω  νεα θεση πρακτικησ
	public String getNewPositionForm(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String currentUsername = authentication.getName();
	    
	    TraineeshipPosition position = new TraineeshipPosition();
		Company company = companyService.retrieveProfile(currentUsername);
	    position.setCompany(company); //βαζω την θεση αυτη στην εταιρεια
	    company.getPositions().add(position);//αυτο γινεται γιανα χω συνδεση μεταξυ πρακτικης και εταιρειασ
		
		model.addAttribute("position", position);
		
		return "company/new-position";
	}
	
	@RequestMapping("/company/create-position")
	public String createNewPosition(@ModelAttribute("position") TraineeshipPosition position) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String currentUsername = authentication.getName();
	    
	    Company company = companyService.retrieveProfile(currentUsername);
	    position.setStudent(null); 
	    position.setCompany(company);
		
		traineeshipService.save(position); 
		
		return "redirect:/company/dashboard";
	}
	
	@PostMapping("/company/delete-position/{id}")//οι αγκυλες χρησιμοποιουνται γιανα δειξουμε οτι περναμε μια μεταβλητη μεσα απτο URL
	public String deletePosition(@PathVariable Integer id) {//το @PathVariable σημαινει οτι βρισκω το id απτο μονοπατι πανω
		
		traineeshipService.deletePosition(id);//και εκεινο το id το διαγραφω ωστε να διαγραφτει και ολη η θεση
											//δες την μεθοδο απτο traineeshipService
		return "redirect:/company/dashboard";
	}
	
	@RequestMapping("/company/assigned-positions/{id}/evaluation")
	public String getEvaluationForm(Model model, @PathVariable int id) {
		model.addAttribute("posId", id);
		model.addAttribute("evaluation", new Evaluation());
		return "company/evaluation";
	}
	
	@RequestMapping("/company/assigned-positions/{id}/evaluation/save")
	public String saveEvaluation(@ModelAttribute Evaluation evaluation, @PathVariable int id) {
		evaluation.setEvaluationType(EvaluationType.COMPANY);
		companyService.saveEvaluation(id, evaluation);
		return "redirect:/company/dashboard";
	}
	
}
