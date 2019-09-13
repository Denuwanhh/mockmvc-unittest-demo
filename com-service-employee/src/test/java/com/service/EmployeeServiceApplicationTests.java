package com.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(com.service.controller.EmployeeController.class)
public class EmployeeServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private com.service.service.EmployeeService employeeService;
	
	@Test
	public void Employee_WhenAccessRoot_GetAllEmployees() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/employee").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	

}
