package com.api.employee_management.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.api.employee_management.configuration.JwtUtil;
import com.api.employee_management.controllers.dtos.AuthRequest;
import com.api.employee_management.services.CustomUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

	private final JwtUtil jwtUtil;
	private final CustomUserDetailsService userDetailsService;

	public AuthenticationController(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}

	@PostMapping
	public ResponseEntity<String> authenticate(@RequestBody AuthRequest authRequest) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());


			// Extract roles from UserDetails
		List<String> roles = userDetails.getAuthorities().stream()
					.map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList());

			// Generate JWT token with roles
		String token = jwtUtil.generateToken(authRequest.getUsername(), roles);
		return ResponseEntity.ok(token);
	}
}
