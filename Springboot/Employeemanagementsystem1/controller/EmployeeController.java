package com.divya.springbootwithangular.Employeemanagementsystem1.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divya.springbootwithangular.Employeemanagementsystem1.exception.ResourceNotFoundException;
import com.divya.springbootwithangular.Employeemanagementsystem1.model.Employee;
import com.divya.springbootwithangular.Employeemanagementsystem1.repository.EmployeeRepository;

@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/api/v1/")
public class EmployeeController {
	@Autowired
	private EmployeeRepository emprep;
	
	// get all employees
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return emprep.findAll();
	}
	
	//create employee rest api
	@PostMapping("/employees")
	public Employee createEmp(@RequestBody Employee employee) {
		return emprep.save(employee);
	}
	
	//get employee by id rest api
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
		Employee employee =emprep.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id:" +id));
		return ResponseEntity.ok(employee);
	}
	  
	// update employee rest api
	
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable int id, @RequestBody Employee employeeDetails) {
		Employee employee =emprep.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id:" +id));
		
		employee.setName(employeeDetails.getName());
		employee.setDept(employeeDetails.getDept());
		employee.setSalary(employeeDetails.getSalary());
		
		Employee updatedEmployee = emprep.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	
	}
 
	// delete employee rest api
	
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable int id){
		Employee employee =emprep.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id:" +id));
		
		emprep.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
