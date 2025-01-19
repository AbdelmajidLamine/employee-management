package com.api.employee_management.controllers;

import java.util.List;

import com.api.employee_management.controllers.dtos.EmployeeRequestDto;
import com.api.employee_management.controllers.dtos.EmployeeResponseDto;
import com.api.employee_management.models.Employee;
import com.api.employee_management.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import com.api.employee_management.services.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
@Slf4j
public class EmployeeController {
	private final EmployeeService employeeService;
	private final UserRepository userRepository;

	public EmployeeController(EmployeeService employeeService, UserRepository userRepository) {this.employeeService = employeeService;
		this.userRepository = userRepository;
	}

	@PostMapping
	public List<EmployeeResponseDto> searchEmployees(@RequestBody EmployeeRequestDto employeeRequestDto, @AuthenticationPrincipal User authenticatedUser) {
        extractUser(employeeRequestDto, authenticatedUser);
		return employeeService.searchEmployees(employeeRequestDto, authenticatedUser.getUsername());
	}

	@GetMapping("/{employeeId}")
	public EmployeeResponseDto getEmployee(@PathVariable String employeeId, @AuthenticationPrincipal User authenticatedUser) {
		return employeeService.getEmployeeById(employeeId, authenticatedUser.getUsername());
	}

	@PostMapping("/add")
	public EmployeeResponseDto createEmployee(@RequestBody Employee employee, @AuthenticationPrincipal User authenticatedUser) {
		return employeeService.createEmployee(employee, authenticatedUser.getUsername());
	}

	@PutMapping("/{employeeId}")
	public EmployeeResponseDto updateEmployee(@PathVariable String employeeId,
	                                  @RequestBody EmployeeRequestDto employeeRequestDto , @AuthenticationPrincipal User authenticatedUser) {
		extractUser(employeeRequestDto, authenticatedUser);
		return employeeService.updateEmployee(employeeId, employeeRequestDto, authenticatedUser.getUsername());
	}

	@DeleteMapping("/{employeeId}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable String employeeId, @AuthenticationPrincipal User authenticatedUser) {
		employeeService.deleteEmployee(employeeId, authenticatedUser.getUsername());
		return ResponseEntity.ok().build();
	}

	public void extractUser(EmployeeRequestDto employeeRequestDto, User authenticatedUser) {
		boolean isManager = authenticatedUser.getAuthorities().stream()
				.anyMatch(auth -> auth.getAuthority().equals("ROLE_MANAGER"));
		com.api.employee_management.models.User user = userRepository.findByUsername(authenticatedUser.getUsername())
				.orElseThrow(() -> new RuntimeException("User not found"));

		if (isManager) {
			employeeRequestDto.setDepartment(user.getRoles().get(0).getDepartement());
		}
	}
}
