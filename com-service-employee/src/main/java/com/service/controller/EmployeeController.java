package com.service.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.service.entity.Employee;
import com.service.service.EmployeeService;

@RestController()
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping
	public List<Employee> getAllEmployees() {
		return employeeService.getAllEmployees();
	}

	@PostMapping
	public ResponseEntity<Employee> addNewEmployee(@Valid @RequestBody Employee employee) {
		return employeeService.addNewEmployee(employee) != null
				? new ResponseEntity<Employee>(employee, HttpStatus.CREATED)
				: new ResponseEntity<Employee>(employee, HttpStatus.CONFLICT);
	}

	@PutMapping(path = "/{employeeID}")
	public ResponseEntity<Employee> updateEmployee(@RequestBody Employee newEmployee,
			@PathVariable("employeeID") Integer employeeID) {

		Employee employee = employeeService.updateEmployee(newEmployee, employeeID);

		return employee != null ? new ResponseEntity<Employee>(employee, HttpStatus.OK)
				: new ResponseEntity<Employee>(newEmployee, HttpStatus.NOT_FOUND);
	}

	@DeleteMapping(path = "/{employeeID}")
	public ResponseEntity<?> deleteEmployee(@PathVariable("employeeID") Integer employeeID) {
		return employeeService.deleteEmployee(employeeID) ? new ResponseEntity<>(HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
}
