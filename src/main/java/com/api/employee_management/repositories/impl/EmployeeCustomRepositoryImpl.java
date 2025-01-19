package com.api.employee_management.repositories.impl;

import java.util.ArrayList;
import java.util.List;

import com.api.employee_management.controllers.dtos.EmployeeRequestDto;
import com.api.employee_management.models.Employee;
import com.api.employee_management.repositories.EmployeeCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeCustomRepositoryImpl implements EmployeeCustomRepository {
	@PersistenceContext
	private final EntityManager entityManager;

	public EmployeeCustomRepositoryImpl(EntityManager entityManager) {this.entityManager = entityManager;}

	@Override
	public List<Employee> search(final EmployeeRequestDto employeeSearchRequestDto) {
		final CriteriaQuery<Employee> cq = getEmployeeCriteriaQuery(employeeSearchRequestDto);
		return entityManager.createQuery(cq).getResultList();
	}

	private CriteriaQuery<Employee> getEmployeeCriteriaQuery(EmployeeRequestDto employeeSearchRequestDto) {
		final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
		final Root<Employee> employee = cq.from(Employee.class);
		cq.select(employee);

		List<Predicate> predicates = new ArrayList<>();

		if (employeeSearchRequestDto.getFullName() != null) {
			predicates.add(cb.like(cb.upper(employee.get("fullName")),
			                       "%" + employeeSearchRequestDto.getFullName().toUpperCase() + "%"));
		}

		if (employeeSearchRequestDto.getEmployeeId() != null) {
			predicates.add(cb.equal(employee.get("employeeId"), employeeSearchRequestDto.getEmployeeId()));
		}

		if (employeeSearchRequestDto.getDepartment() != null) {
			predicates.add(cb.like(cb.upper(employee.get("department")),
			                       "%" + employeeSearchRequestDto.getDepartment().toUpperCase() + "%"));
		}

		if (employeeSearchRequestDto.getJobTitle() != null) {
			predicates.add(cb.like(cb.upper(employee.get("jobTitle")),
			                       "%" + employeeSearchRequestDto.getJobTitle().toUpperCase() + "%"));
		}

		if (employeeSearchRequestDto.getHireDate() != null) {
			predicates.add(cb.equal(employee.get("hireDate"), employeeSearchRequestDto.getHireDate()));
		}

		if (employeeSearchRequestDto.getEmploymentStatus() != null) {
			predicates.add(cb.equal(employee.get("employmentStatus"), employeeSearchRequestDto.getEmploymentStatus()));
		}

		cq.where(predicates.toArray(new Predicate[0]));
		return cq;
	}
}
