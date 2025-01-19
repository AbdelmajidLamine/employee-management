package com.api.employee_management.ui.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AuthenticationAPI {
	private static final String BASE_URL = "http://localhost:8080/authenticate";
	public static String authenticateUser(String username, String password) {
		try {
			URL url = new URL(BASE_URL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");

			String data = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
			try (OutputStream os = connection.getOutputStream()) {
				os.write(data.getBytes());
			}

			int responseCode = connection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
					return br.readLine();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
