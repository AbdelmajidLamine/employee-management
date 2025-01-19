package com.api.employee_management.ui.api;

import com.api.employee_management.controllers.dtos.EmployeeRequestDto;
import com.api.employee_management.controllers.dtos.EmployeeResponseDto;
import com.api.employee_management.models.Employee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class EmployeeAPI {
	private static final String BASE_URL = "http://localhost:8080/employees";
	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static List<EmployeeResponseDto> searchEmployees(EmployeeRequestDto employeeRequestDto , String token) {
		try {
			// Define the URL for the endpoint
			String url = BASE_URL;

			// Convert the EmployeeRequestDto object to JSON
			String jsonPayload = objectMapper.writeValueAsString(employeeRequestDto);

			// Set up the HTTP connection
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", "Bearer " + token);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);

			// Write the JSON payload to the request body
			try (OutputStream outputStream = connection.getOutputStream()) {
				outputStream.write(jsonPayload.getBytes(StandardCharsets.UTF_8));
				outputStream.flush();
			}

			// Read the response
			int responseCode = connection.getResponseCode();
			if (responseCode == 200) {
				InputStream inputStream = connection.getInputStream();
				return objectMapper.readValue(inputStream, new TypeReference<List<EmployeeResponseDto>>() {});
			} else {
				System.err.println("Error fetching employees: HTTP " + responseCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Return an empty list in case of failure
		return new ArrayList<>();
	}


	public static void addEmployee(EmployeeRequestDto employee, String token) {
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(BASE_URL + "/add").openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", "Bearer " + token);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);

			String employeeJson = objectMapper.writeValueAsString(employee);
			try (OutputStream outputStream = connection.getOutputStream()) {
				outputStream.write(employeeJson.getBytes());
				outputStream.flush();
			}

			int responseCode = connection.getResponseCode();
			if (responseCode != 201) {
				System.err.println("Error adding employee: " + responseCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static EmployeeResponseDto getEmployeeById(String employeeId, String token) throws Exception {
		String url = BASE_URL + "/" + employeeId;
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Authorization", "Bearer " + token);
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setDoOutput(true);

		int responseCode = connection.getResponseCode();
		if (responseCode == 200) {
			InputStream inputStream = connection.getInputStream();
			return objectMapper.readValue(inputStream, new TypeReference<EmployeeResponseDto>() {});
		} else {
			System.err.println("Error fetching employees: HTTP " + responseCode);
		}
		return new EmployeeResponseDto();
	}

	public static void updateEmployee(EmployeeRequestDto employee, String token) {
		try {
			String url = BASE_URL + "/" + employee.getEmployeeId();
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setRequestMethod("PUT");
			connection.setRequestProperty("Authorization", "Bearer " + token);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);

			String employeeJson = objectMapper.writeValueAsString(employee);
			try (OutputStream outputStream = connection.getOutputStream()) {
				outputStream.write(employeeJson.getBytes());
				outputStream.flush();
			}

			int responseCode = connection.getResponseCode();
			if (responseCode != 200) {
				System.err.println("Error updating employee: " + responseCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void deleteEmployee(String employeeId, String token) {
		try {
			String url = BASE_URL + "/" + employeeId;
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setRequestMethod("DELETE");
			connection.setRequestProperty("Authorization", "Bearer " + token);
			connection.setRequestProperty("Content-Type", "application/json");

			int responseCode = connection.getResponseCode();
			if (responseCode != 200) {
				System.err.println("Error deleting employee: " + responseCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
