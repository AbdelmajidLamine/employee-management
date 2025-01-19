package com.api.employee_management.repositories;

import java.util.Optional;

import com.api.employee_management.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
}