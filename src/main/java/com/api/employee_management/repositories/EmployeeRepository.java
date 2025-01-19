package com.api.employee_management.repositories;

import java.util.Optional;

import com.api.employee_management.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> , EmployeeCustomRepository{
	Optional<Employee> findByEmployeeId(String employeeId);
}
