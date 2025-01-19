package com.api.employee_management.controllers.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class EmployeeRequestDto {
	private String employeeId;
	private String fullName;
	private String jobTitle;
	private String department;
	private String employmentStatus;
	private String hireDate;
	private String contactInfo;
	private String address;

	public EmployeeRequestDto() {
	}

	public EmployeeRequestDto(final String employeeId, final String fullName, final String jobTitle, final String department,
	                          final String employmentStatus, final String hireDate) {
		this.employeeId = employeeId;
		this.fullName = fullName;
		this.jobTitle = jobTitle;
		this.department = department;
		this.employmentStatus = employmentStatus;
		this.hireDate = hireDate;
	}

	public EmployeeRequestDto(final String employeeId, final String fullName, final String jobTitle, final String department, final String employmentStatus,
	                          final String hireDate, final String contactInfo,
	                          final String address) {
		this.employeeId = employeeId;
		this.fullName = fullName;
		this.jobTitle = jobTitle;
		this.department = department;
		this.employmentStatus = employmentStatus;
		this.hireDate = hireDate;
		this.contactInfo = contactInfo;
		this.address = address;
	}
}
