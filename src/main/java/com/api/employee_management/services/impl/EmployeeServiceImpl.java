package com.api.employee_management.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.api.employee_management.controllers.dtos.EmployeeRequestDto;
import com.api.employee_management.controllers.dtos.EmployeeResponseDto;
import com.api.employee_management.models.Employee;
import com.api.employee_management.repositories.EmployeeRepository;
import com.api.employee_management.services.EmployeeService;
import com.api.employee_management.transformators.EmployeeTransformator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
	private final EmployeeRepository employeeRepository;
	private final EmployeeTransformator employeeTransformator;

	public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeTransformator employeeTransformator) {
		this.employeeRepository = employeeRepository;
		this.employeeTransformator = employeeTransformator;
	}

	@Override
	public EmployeeResponseDto createEmployee(final Employee employee, final String authenticatedUser) {
		log.info("User '{}' is creating a new employee at {}", authenticatedUser, LocalDateTime.now());
		Employee employeeSaved = employeeRepository.save(employee);
		log.info("Employee created successfully by '{}' with ID: {} at {}", authenticatedUser, employeeSaved.getId(), LocalDateTime.now());
		return employeeTransformator.EmployeeToEmployeeDto(employeeSaved);
	}

	@Override
	public EmployeeResponseDto getEmployeeById(final String employeeId, final String authenticatedUser) {
		log.info("User '{}' is fetching employee with ID: {} at {}", authenticatedUser, employeeId, LocalDateTime.now());
		Employee employee = employeeRepository.findByEmployeeId(employeeId)
				.orElseThrow(() -> {
					log.error("Employee not found with ID: {} by user '{}' at {}", employeeId, authenticatedUser, LocalDateTime.now());
					return new RuntimeException("Employee not found");
				});
		log.info("Employee fetched successfully by '{}': {} at {}", authenticatedUser, employee, LocalDateTime.now());
		return employeeTransformator.EmployeeToEmployeeDto(employee);
	}

	@Override
	public EmployeeResponseDto updateEmployee(final String employeeId, final EmployeeRequestDto employee, final String authenticatedUser) {
		log.info("User '{}' is updating employee with ID: {} at {}", authenticatedUser, employeeId, LocalDateTime.now());
		Employee existingEmployee = employeeRepository.findByEmployeeId(employeeId)
				.orElseThrow(() -> {
					log.error("Employee not found with ID: {} by user '{}' at {}", employeeId, authenticatedUser, LocalDateTime.now());
					return new RuntimeException("Employee not found");
				});

		log.info("User '{}' is modifying employee details: {} at {}", authenticatedUser, existingEmployee, LocalDateTime.now());
		existingEmployee.setFullName(employee.getFullName());
		existingEmployee.setJobTitle(employee.getJobTitle());
		existingEmployee.setDepartment(employee.getDepartment());
		existingEmployee.setHireDate(LocalDate.parse(employee.getHireDate()));
		existingEmployee.setEmploymentStatus(employee.getEmploymentStatus());
		existingEmployee.setContactInfo(employee.getContactInfo());
		existingEmployee.setAddress(employee.getAddress());

		Employee updatedEmployee = employeeRepository.save(existingEmployee);
		log.info("Employee updated successfully by '{}': {} at {}", authenticatedUser, updatedEmployee, LocalDateTime.now());
		return employeeTransformator.EmployeeToEmployeeDto(updatedEmployee);
	}

	@Override
	public void deleteEmployee(final String employeeId, final String authenticatedUser) {
		log.info("User '{}' is deleting employee with ID: {} at {}", authenticatedUser, employeeId, LocalDateTime.now());
		Employee employee = employeeRepository.findByEmployeeId(employeeId)
				.orElseThrow(() -> {
					log.error("Employee not found with ID: {} by user '{}' at {}", employeeId, authenticatedUser, LocalDateTime.now());
					return new RuntimeException("Employee not found");
				});

		employeeRepository.delete(employee);
		log.info("Employee deleted successfully by '{}' with ID: {} at {}", authenticatedUser, employeeId, LocalDateTime.now());
	}

	@Override
	public List<EmployeeResponseDto> searchEmployees(final EmployeeRequestDto employeeRequestDto, final String authenticatedUser) {
		log.info("User '{}' is searching for employees with criteria: {} at {}", authenticatedUser, employeeRequestDto, LocalDateTime.now());
		List<Employee> employees = employeeRepository.search(employeeRequestDto);
		log.info("User '{}' found {} employees matching the criteria at {}", authenticatedUser, employees.size(), LocalDateTime.now());
		return employeeTransformator.EmployeesToEmployeeDtos(employees);
	}
}
