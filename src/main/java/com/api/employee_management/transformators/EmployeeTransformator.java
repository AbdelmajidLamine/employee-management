package com.api.employee_management.transformators;

import java.util.List;
import java.util.stream.Collectors;

import com.api.employee_management.controllers.dtos.EmployeeResponseDto;
import com.api.employee_management.models.Employee;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class EmployeeTransformator {
	private static ModelMapper modelMapper = null;
	public static ModelMapper modelMapper() {
		if (null == modelMapper) {
			modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			modelMapper.getConfiguration().setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
		}
		return modelMapper;
	}

	@Transactional
	public EmployeeResponseDto EmployeeToEmployeeDto(Employee employee) {
		if (null == employee) {
			return null;
		} else {
			return modelMapper().map(employee, EmployeeResponseDto.class);
		}
	}

	@Transactional
	public List<EmployeeResponseDto> EmployeesToEmployeeDtos(List<Employee> utilisateurs) {
		return utilisateurs.stream().map(this::EmployeeToEmployeeDto).collect(Collectors.toList());
	}
}
