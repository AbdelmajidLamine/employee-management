package com.api.employee_management.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@Builder
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Column(unique = true)
	@Size(min = 5, max = 10)
	private String employeeId;
	private String fullName;
	private String jobTitle;
	private String department;
	private String employmentStatus;
	private LocalDate hireDate;
	private String contactInfo;
	private String address;

	public Employee(final Long id, final String fullName, final String jobTitle, final String department,
	                final String employmentStatus, final LocalDate hireDate) {
		this.id = id;
		this.fullName = fullName;
		this.jobTitle = jobTitle;
		this.department = department;
		this.employmentStatus = employmentStatus;
		this.hireDate = hireDate;
	}

	public Employee(){

	}
}
