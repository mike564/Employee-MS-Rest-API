package com.fdmgroup.EmployeeRestAPI.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdmgroup.EmployeeRestAPI.exceptions.EmployeeNotFoundException;
import com.fdmgroup.EmployeeRestAPI.model.Employee;
import com.fdmgroup.EmployeeRestAPI.repositories.EmployeeRepository;

@Service
public class EmployeeAPIService {
	private EmployeeRepository empRepo;

	public EmployeeAPIService() {
		super();
	}

	@Autowired
	public EmployeeAPIService(EmployeeRepository empRepo) {
		super();
		this.empRepo = empRepo;
	}
	
	public Employee createEmployee(Employee employee) {
		return empRepo.save(employee);
	}
	
	public Employee getEmployeeById(Long employeeId) {
		Optional<Employee> optEmployee = empRepo.findById(employeeId);
		if(optEmployee.isEmpty()) {
			throw new EmployeeNotFoundException("Employee ID:" + employeeId + " not found");
		}
		return optEmployee.get();
	}
	
	public List<Employee> getAllEmployees() {
		List<Employee> employees = empRepo.findAll();
		return employees;
	}
	
	@Transactional
	public Employee updateEmployeeFullNameAndSalary(Employee employee) {
		if(empRepo.findById(employee.getId()).isEmpty()) {
			throw new EmployeeNotFoundException("Employee ID:" + employee.getId() + " not found");
		}
		empRepo.saveFullNameAndSalary(employee.getFirstName(),employee.getLastName(),employee.getSalary(),employee.getId());
		
		Optional<Employee> updatedEmployee = empRepo.findById(employee.getId());
		return updatedEmployee.get();
	}
	
	
	public Employee deleteEmployeeByID(Long employeeId) {
		Optional<Employee> optEmployee = empRepo.findById(employeeId);
		if(optEmployee.isEmpty()) {
			throw new EmployeeNotFoundException("Employee ID:" + employeeId + " not found");
		}
		empRepo.delete(optEmployee.get());
		return optEmployee.get();
	}
	
	public List<Employee> findAllEmployeesByFullName(String firstName, String lastName) {
		List<Employee> employees = empRepo.findByFullName(firstName, lastName);
		return employees;
	}
	
	
}
