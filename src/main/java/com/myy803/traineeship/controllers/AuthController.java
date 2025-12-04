package com.myy803.traineeship.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myy803.traineeship.domainmodel.User;
import com.myy803.traineeship.services.UserService;
//AFTH H KLASH XREIAZETAI GIA TO ARXIKO LOGIN KAI REGISTER(sign in/sing up)
@Controller
public class AuthController {
    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public String login(){
        return "auth/signin";
    }

    @RequestMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());//περναμε το user σαν νεο αντικειμενο στο html arxeio 
        return "auth/signup";
    }

    @RequestMapping("/save")
    public String registerUser(@ModelAttribute("user") User user, Model model){//εδω περνουμε πισω το user απτην html
       
        if(userService.isUserPresent(user)){//και ελεγχουμε αν υπαρχει ηδη 
            model.addAttribute("successMessage", "User already registered!");//τοτε τυπωνουμε μνμα υπαρχει ηδη
            return "auth/signin";
        }

        userService.saveUser(user);//αλλιως τον αποθηκευουμε 
        model.addAttribute("successMessage", "User registered successfully!");//τυπωνουμε μνμα εγινε ρετζιστερ επιτυχως

        return "auth/signin";
    }
}