package com.myy803.traineeship.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myy803.traineeship.domainmodel.Student;
import com.myy803.traineeship.domainmodel.TraineeshipPosition;
import com.myy803.traineeship.services.StudentService;
import com.myy803.traineeship.services.TraineeshipService;

@Controller
public class StudentController {

	@Autowired
	StudentService studentService;
	
	@Autowired
	TraineeshipService traineeshipService;
	
	@RequestMapping("/student/dashboard")
    public String getStudentDashboard(Model model){

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String currentUsername = authentication.getName();
	    
		if (studentService.isStudentPresent(currentUsername)) {//an iparhei o student me to sigkekrimeno username
			model.addAttribute("student", studentService.retrieveProfile(currentUsername));//perna to adistoixo profil sthn html
			return "student/dashboard";
		}
		else {
			return "redirect:/student/profile";//alliws pigene sto profil tou gia na to siblirwseis
		}
    }
	
	@RequestMapping("/student/profile") //tha siblirwsw profil kai tha  to δωσω sthn html
    public String retrieveProfile(Model model){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String currentUsername = authentication.getName();
	    
		model.addAttribute("student", studentService.retrieveProfile(currentUsername));
		
		return "student/profile";
    }
	
	@RequestMapping("/student/profile/save")
    public String saveProfile(@ModelAttribute("student") Student student){//to pernw apthn html to adikeimeno student pou siblirwsa to profil tou apo panw  
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String currentUsername = authentication.getName();
	    
	    student.setUsername(currentUsername);// το ξανα θετω
		studentService.saveProfile(student);//apothikevw to profil tou στην Β.Δ. mesw ths klashs service kai afth mesw tou mapper
		
		return "redirect:/student/dashboard";
    }
	
	@RequestMapping("/student/apply") //edw o foithths tha kanei apply gia praktikh
	public String applyForTraineeship() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String currentUsername = authentication.getName();//pernw to username tou foithth pou exei kanie login
		
	    Student student = studentService.retrieveProfile(currentUsername);//me bash to username sou epistrefei to profil tou student
	    student.setLookingForTraineeship(true);
	    studentService.saveProfile(student);//mesw tou studentservicelmpl apothikevw ton foithth
		
		return "redirect:/student/dashboard";
	}
	
	@RequestMapping("/student/fill-logbook") //φιλλαρω το λογκμπουκ και το στελνω ως στηλη στον πινακα(SQL) tou trainiship position
	public String fillLogbook(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String currentUsername = authentication.getName();
	    
	    model.addAttribute("position", traineeshipService.retrievePosition(currentUsername));//apto service mesw tou retrievePosition pigainei sto traineeship position mapper
	    													//h retrievePosition epistrefei assigned position tou foithth
		return "student/fill-logbook";
	}
	
	@RequestMapping("/student/savelogbook") //afoy to fillarw to kanw save kai me ksanapaei sto dashboard
	public String saveLogbook(@RequestParam("logbook") String logbook) {
		traineeshipService.saveLogbook(logbook);
		
		return "redirect:/student/dashboard";
	}
	
	
}
