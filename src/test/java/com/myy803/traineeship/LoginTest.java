package com.myy803.traineeship;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest {

	@Autowired
    private MockMvc mockMvc;

	@Test
    public void testLoginPage() throws Exception { //kanei test sthn ousia to login tou authcontroller
        mockMvc.perform(get("/login")) //to mockmvc mimeitai oti benei sthn selida login
               .andExpect(status().isOk()) //an bike odws sto login to site einai ok 
               .andExpect(view().name("auth/signin"));  //an epistrefei auth signin
    }
		
	@Test
	@WithMockUser(username = "company", roles = {"COMPANY"}) //san na kanei fake oti exw kanei sindesei sto programma ws compsny
	void testLogout() throws Exception {
	    mockMvc.perform(get("/logout"))
	            .andExpect(status().is3xxRedirection()) //otan kanw logput perimenw na me kanei redirect sto login
	            .andExpect(redirectedUrl("/")); //afto exei valei o zaras ws suucess logout( / )
	}
	
	@Test
	void testAccessToDashboardWithoutAuth() throws Exception { //AN PROSPATHISOUME NA PAME APEFTEIAS STO DASHBOARD(GRAFWDAS TO STO URL)
	    mockMvc.perform(get("/company/dashboard"))			  //XWRIS NA XOYME KANEI AKOMA REGISTER THA MAS GYRISEI AFTOMATA STO LOGIN
	            .andExpect(status().is3xxRedirection())
	            .andExpect(redirectedUrl("http://localhost/login"));
	}
}
