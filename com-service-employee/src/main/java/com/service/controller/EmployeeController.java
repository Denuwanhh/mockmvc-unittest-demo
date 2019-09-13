package com.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.entity.Employee;
import com.service.service.EmployeeService;

@RestController("/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping
	public List<Employee> getAllEmployees(){
		return employeeService.getAllEmployees();
	} 
}
