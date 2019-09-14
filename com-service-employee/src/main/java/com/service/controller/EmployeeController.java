package com.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.service.entity.Employee;
import com.service.service.EmployeeService;

@RestController("/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping
	public List<Employee> getAllEmployees() {
		return employeeService.getAllEmployees();
	}

	@PostMapping
	public ResponseEntity<Employee> addNewEmployee(@RequestBody Employee employee) {
		ResponseEntity<Employee> responseEntity = null;
		try {
			responseEntity = employeeService.addNewEmployee(employee) != null
					? new ResponseEntity<Employee>(employee, HttpStatus.CREATED)
					: new ResponseEntity<Employee>(employee, HttpStatus.CONFLICT);
		} catch (Exception ex) {
			responseEntity = new ResponseEntity<Employee>(employee, HttpStatus.BAD_REQUEST);
		}
		return responseEntity;
	}
}
