package com.api.employee_management.configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final UserDetailsService userDetailsService;
	private final JwtRequestFilter jwtRequestFilter;

	public SecurityConfig(UserDetailsService userDetailsService, JwtRequestFilter jwtRequestFilter) {
		this.userDetailsService = userDetailsService;
		this.jwtRequestFilter = jwtRequestFilter;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.httpBasic(AbstractHttpConfigurer::disable)
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
						.requestMatchers(AntPathRequestMatcher.antMatcher("/authenticate")).permitAll()
						.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/employees/{id}")).hasRole("HR")
						.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/employees")).hasAnyRole("HR", "MANAGER","ADMIN")
						.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/employees/add")).hasRole("HR")
						.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT, "/employees/**")).hasAnyRole("HR", "MANAGER")
						.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE, "/employees/**")).hasRole("HR")
						.requestMatchers(AntPathRequestMatcher.antMatcher("/admin/**")).hasRole("ADMIN")
						.anyRequest().authenticated())
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder =
				http.getSharedObject(AuthenticationManagerBuilder.class);

		authenticationManagerBuilder
				.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder());

		return authenticationManagerBuilder.build();
	}

//	@Bean
//	public static PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();  // No password encoding
	}

	private CorsFilter corsFilter() {
		final CorsConfiguration config = new CorsConfiguration();
		config.setAllowedHeaders(Collections.singletonList("*"));
		config.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "DELETE", "OPTIONS", "PATCH"));
		config.setAllowedOriginPatterns(List.of("*"));
		config.setAllowCredentials(true);
		config.setMaxAge(1800L);
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
}
