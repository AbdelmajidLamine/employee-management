package com.api.employee_management.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponseDto {
	private String employeeId;
	private String fullName;
	private String jobTitle;
	private String department;
	private String hireDate;
	private String employmentStatus;
	private String contactInfo;
	private String address;
}
