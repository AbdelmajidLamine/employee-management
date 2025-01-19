package com.api.employee_management.services;

import java.util.List;

import com.api.employee_management.controllers.dtos.EmployeeRequestDto;
import com.api.employee_management.controllers.dtos.EmployeeResponseDto;
import com.api.employee_management.models.Employee;

public interface EmployeeService {
	EmployeeResponseDto createEmployee(final Employee employee, final String authenticatedUser);
	EmployeeResponseDto getEmployeeById(final String employeeId, final String authenticatedUser);
	EmployeeResponseDto updateEmployee(final String employeeId, final EmployeeRequestDto employeeRequestDto, final String authenticatedUser);
	void deleteEmployee(final String employeeId, final String authenticatedUser);
	List<EmployeeResponseDto> searchEmployees(final EmployeeRequestDto employeeRequestDto, final String authenticatedUser);

}
