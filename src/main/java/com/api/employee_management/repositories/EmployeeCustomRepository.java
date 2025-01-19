package com.api.employee_management.repositories;

import java.util.List;

import com.api.employee_management.controllers.dtos.EmployeeRequestDto;
import com.api.employee_management.models.Employee;

public interface EmployeeCustomRepository {
	List<Employee> search(EmployeeRequestDto employeeSearchRequestDto);
}
