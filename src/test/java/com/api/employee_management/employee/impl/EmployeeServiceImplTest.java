package com.api.employee_management.employee.impl;

import com.api.employee_management.controllers.dtos.EmployeeRequestDto;
import com.api.employee_management.controllers.dtos.EmployeeResponseDto;
import com.api.employee_management.models.Employee;
import com.api.employee_management.repositories.EmployeeRepository;
import com.api.employee_management.services.impl.EmployeeServiceImpl;
import com.api.employee_management.transformators.EmployeeTransformator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

	@Mock
	private EmployeeRepository employeeRepository;

	@Mock
	private EmployeeTransformator employeeTransformator;

	private EmployeeServiceImpl employeeService;

	private Employee employee;
	private EmployeeResponseDto employeeResponseDto;

	@BeforeEach
	void setUp() {
		employee = new Employee();
		employee.setId(1L);
		employee.setEmployeeId("S12345");
		employee.setFullName("ABDELMAJID LAMINE");
		employee.setJobTitle("Software Engineer");
		employee.setDepartment("IT");
		employee.setHireDate(LocalDate.of(2020, 1, 15));
		employee.setEmploymentStatus("Active");

		employeeResponseDto = new EmployeeResponseDto();
		employeeResponseDto.setEmployeeId("S12345");
		employeeResponseDto.setFullName("ABDELMAJID LAMINE");
		employeeResponseDto.setJobTitle("Software Engineer");

		employeeService = new EmployeeServiceImpl(employeeRepository , employeeTransformator);
	}

	@Test
	void testCreateEmployee() {
		when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
		when(employeeTransformator.EmployeeToEmployeeDto(employee)).thenReturn(employeeResponseDto);

		EmployeeResponseDto result = employeeService.createEmployee(employee, "admin");

		assertNotNull(result);
		assertEquals("S12345", result.getEmployeeId());
		assertEquals("ABDELMAJID LAMINE", result.getFullName());
		verify(employeeRepository, times(1)).save(employee);
	}

	@Test
	void testGetEmployeeById() {
		when(employeeRepository.findByEmployeeId("S12345")).thenReturn(Optional.of(employee));
		when(employeeTransformator.EmployeeToEmployeeDto(employee)).thenReturn(employeeResponseDto);

		EmployeeResponseDto result = employeeService.getEmployeeById("S12345", "admin");

		assertNotNull(result);
		assertEquals("S12345", result.getEmployeeId());
		verify(employeeRepository, times(1)).findByEmployeeId("S12345");
	}

	@Test
	void testGetEmployeeByIdNotFound() {
		when(employeeRepository.findByEmployeeId("S12345")).thenReturn(Optional.empty());

		RuntimeException exception = assertThrows(RuntimeException.class, () ->
				employeeService.getEmployeeById("S12345", "admin"));

		assertEquals("Employee not found", exception.getMessage());
		verify(employeeRepository, times(1)).findByEmployeeId("S12345");
	}

	@Test
	void testUpdateEmployee() {
		EmployeeRequestDto requestDto = new EmployeeRequestDto();
		requestDto.setFullName("ABDELMAJID LAMINE");
		requestDto.setJobTitle("Senior Engineer");
		requestDto.setDepartment("IT");
		requestDto.setHireDate("2021-05-01");
		requestDto.setEmploymentStatus("Active");

		when(employeeRepository.findByEmployeeId("S12345")).thenReturn(Optional.of(employee));
		when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
		when(employeeTransformator.EmployeeToEmployeeDto(employee)).thenReturn(employeeResponseDto);

		EmployeeResponseDto result = employeeService.updateEmployee("S12345", requestDto, "admin");

		assertNotNull(result);
		assertEquals("S12345", result.getEmployeeId());
		verify(employeeRepository, times(1)).findByEmployeeId("S12345");
		verify(employeeRepository, times(1)).save(employee);
	}

	@Test
	void testDeleteEmployee() {
		when(employeeRepository.findByEmployeeId("S12345")).thenReturn(Optional.of(employee));
		doNothing().when(employeeRepository).delete(employee);

		employeeService.deleteEmployee("S12345", "admin");

		verify(employeeRepository, times(1)).findByEmployeeId("S12345");
		verify(employeeRepository, times(1)).delete(employee);
	}

	@Test
	void testSearchEmployees() {
		EmployeeRequestDto requestDto = new EmployeeRequestDto();
		when(employeeRepository.search(requestDto)).thenReturn(Collections.singletonList(employee));
		when(employeeTransformator.EmployeesToEmployeeDtos(Collections.singletonList(employee)))
				.thenReturn(Collections.singletonList(employeeResponseDto));

		var result = employeeService.searchEmployees(requestDto, "admin");

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals("S12345", result.get(0).getEmployeeId());
		verify(employeeRepository, times(1)).search(requestDto);
	}
}
