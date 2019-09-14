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

		Employee createdEmployee = null;

		if (employee.getEmployeeID() == null)
			createdEmployee = employeeRepository.save(employee);

		return createdEmployee;
	}
}
