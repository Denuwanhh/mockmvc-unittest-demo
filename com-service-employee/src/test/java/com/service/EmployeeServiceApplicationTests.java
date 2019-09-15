package com.service;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.entity.Employee;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Profile("dev")
public class EmployeeServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void Employee_WhenAccessRoot_ShouldGetAllEmployees() throws Exception {

		mockMvc.perform( MockMvcRequestBuilders
			      .get("/employees")
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.[*]").exists())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.[*].employeeID").isNotEmpty());
	}

	@Test
	public void Employee_WhenPost_ShouldGetID() throws Exception {
		Employee employee = new Employee();
		employee.setEmployeeName("Name");
		employee.setEmployeeAddress("Address");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/employees")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(new ObjectMapper().writeValueAsString(employee)))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.employeeID").exists());
	}
	
	@Test
	public void Employee_WhenPostWithID_ShouldNotAllow() throws Exception {
		Employee employee = new Employee();
		employee.setEmployeeID(1);
		employee.setEmployeeName("Name");
		employee.setEmployeeAddress("Address");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/employees")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(new ObjectMapper().writeValueAsString(employee)))
				.andExpect(status().isConflict());
	}
	
	@Test
	public void Employee_WhenPostWithoutName_ShouldNotAllow() throws Exception {
		Employee employee = new Employee();
		employee.setEmployeeAddress("Address");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/employees")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(new ObjectMapper().writeValueAsString(employee)))
				.andExpect(status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.employeeName").value("Employee Name is mandatory"))
				.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void Employee_WhenPutWithEmployeeID_ShouldRecordUpdate() throws Exception {
		Employee employee = new Employee();
		employee.setEmployeeAddress("New Address");
		employee.setEmployeeName("New Names");
		employee.setDateOfBirth(new Date());
		
		mockMvc.perform(MockMvcRequestBuilders.put("/employees/10")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(new ObjectMapper().writeValueAsString(employee)))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.employeeID").value("10"))
				.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void Employee_WhenPutWithEmployeeIDNotExisting_ShouldGetNotFoundStatus() throws Exception {
		Employee employee = new Employee();
		employee.setEmployeeAddress("New Address");
		employee.setEmployeeName("New Names");
		employee.setDateOfBirth(new Date());
		
		mockMvc.perform(MockMvcRequestBuilders.put("/employees/-10")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(new ObjectMapper().writeValueAsString(employee)))
				.andExpect(status().isNotFound())
				.andExpect(MockMvcResultMatchers.jsonPath("$.employeeID").isEmpty())
				.andDo(MockMvcResultHandlers.print());
	}



}
