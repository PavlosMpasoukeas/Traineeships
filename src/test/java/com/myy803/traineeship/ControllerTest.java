package com.myy803.traineeship;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.myy803.traineeship.controllers.CompanyController;
import com.myy803.traineeship.services.CommiteeService;
import com.myy803.traineeship.services.CompanyService;
import com.myy803.traineeship.services.EvaluationService;
import com.myy803.traineeship.services.TraineeshipService;
import com.myy803.traineeship.services.UserService;

@WebMvcTest(CompanyController.class)
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;
    @MockBean
    private UserService userService;
    @MockBean
    private TraineeshipService traineeshipService;
    @MockBean
    private EvaluationService evaluationService;
    @MockBean
    private CommiteeService commiteeService;
    
    @Test
    @WithMockUser(username = "company", roles = {"COMPANY"}) //pali benoume ws company
    void testGetCompanyDashboard() throws Exception {
    	when(companyService.isCompanyPresent("company")).thenReturn(true); //otan iparhei h etaireia dhladh exw kanei hdh register
        
    	mockMvc.perform(get("/company/dashboard")) //phgaine sto company controller na ekteleseis afta pouxei mesa to company/dashboard
                .andExpect(status().isOk()); //perimenoume na mas epistrepsei 
    }
    
    @Test
    @WithMockUser(username = "commitee", roles = {"COMMITEE"}) //benoume ws comitee
    void testProfessorNotFound() throws Exception {
    															//AFOU DEN EXW BALEI KAPOIO WHEN OPWS PANW ARA DN ELEGXW KATI THA MOU BGALEI ERROR OTI DEN BRISKEI TON KATHIGITI
        mockMvc.perform(get("/commitee/assign-professor/3/interests"))
                .andExpect(status().isNotFound());
    }
    
    @Test
    @WithMockUser(username = "company", authorities = {"COMPANY"}) //einai to aditheto apto 1o test
    void testCompanyNotExists() throws Exception {
    	when(companyService.isCompanyPresent("company")).thenReturn(false); //afth th fora dn tha exei kanei register h etaireia 
    	mockMvc.perform(get("/company/dashboard")) 
                .andExpect(status().is3xxRedirection()); //ara perimenoume na ginei redirect sthn selida company-create
    }
}