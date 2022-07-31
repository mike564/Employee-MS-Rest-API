package com.fdmgroup.EmployeeRestAPI.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fdmgroup.EmployeeRestAPI.model.Employee;
import com.fdmgroup.EmployeeRestAPI.service.EmployeeAPIService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/employees")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeAPIController {
	
	private EmployeeAPIService empService;

	public EmployeeAPIController() {
		super();
	}

	@Autowired
	public EmployeeAPIController(EmployeeAPIService empService) {
		super();
		this.empService = empService;
	}
	
	
	@Operation(summary = "Creates a new Employee resource with the Employee that is given.")
	@ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Employee resource successfully created.",
					headers = {@Header(name = "location", description = "URI to access the created resource")},
					content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
					@ApiResponse(responseCode = "400", description = "Employee resource could not be created due to null fields.")
	})
	@PostMapping
	public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
		Employee createdEmployee = empService.createEmployee(employee);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdEmployee.getId()).toUri();
		return ResponseEntity.created(location).body(createdEmployee);
	}
	
	
	@Operation(summary="Retrieves the Employee that has the specified Id")
	@ApiResponses(value={@ApiResponse(responseCode="200",description="Employee resource successfully retreived from the database"),
							@ApiResponse(responseCode="404",description="Employee resource not found in the database")})
	@GetMapping("/{id}")
	public Employee getEmployeeById(@PathVariable("id") long employeeId) {
		return empService.getEmployeeById(employeeId);
	}
	
	
	@Operation(summary="Retrieves an array of all Employees resources in the database")
	@ApiResponse(responseCode="200",description="Employee array retrieval successful")
	@GetMapping
	public List<Employee> getAllEmployees() {
		return empService.getAllEmployees();
	}
	
	
	@Operation(summary="Replaces an Employee resources on the database that has the same Id, using updated name and salary only")
	@ApiResponses(value={@ApiResponse(responseCode="200",description="Employee resources successfully replaced on the database"),
							@ApiResponse(responseCode="404",description="Employee resources not found in the database")})
	@PutMapping
	public Employee updateEmployeeFullNameAndSalary(@Valid @RequestBody Employee employee) {
		return empService.updateEmployeeFullNameAndSalary(employee);
	}
	
	
	@Operation(summary="Removes an Employee resources on the database that has the specified Id")
	@ApiResponses(value={@ApiResponse(responseCode="200",description="Employee resources successfully removed on the database"),
						@ApiResponse(responseCode="404",description="Employee resources not found in the database")})
	@DeleteMapping("/{id}")
	public Employee deleteEmployeeByID(@PathVariable("id") long employeeId) {
		return empService.deleteEmployeeByID(employeeId);
	}
	

	@Operation(summary="Retrieves an array of all Employees resources in the database that have a similiar name")
	@ApiResponse(responseCode="200",description="Employee array retrieval successful")
	@GetMapping("/firstName/{firstName}/lastName/{lastName}")
	public List<Employee> getAllEmployeesByFullName(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
		return empService.findAllEmployeesByFullName(firstName, lastName);
	}
}
