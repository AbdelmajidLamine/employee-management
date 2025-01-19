package com.api.employee_management.repositories;


import com.api.employee_management.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);
}