package com.service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.service.entity.Employee;
import com.service.entity.EmployeeRepository;

@Component
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	public Employee addNewEmployee(Employee employee) {
		return employee.getEmployeeID() == null ? employeeRepository.save(employee) : null;
	}

	public Employee updateEmployee(Employee newEmployee, Integer employeeID) {
		return employeeRepository.findById(employeeID).map(employee -> {
			employee.setEmployeeName(newEmployee.getEmployeeName());
			employee.setEmployeeAddress(newEmployee.getEmployeeAddress());
			employee.setDateOfBirth(newEmployee.getDateOfBirth());
			return employeeRepository.save(employee);
		}).orElseGet(() -> {
			return null;
		});
	}
}
