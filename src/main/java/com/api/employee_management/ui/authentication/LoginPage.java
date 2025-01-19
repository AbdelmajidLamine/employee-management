package com.api.employee_management.ui.authentication;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.*;

import com.api.employee_management.ui.api.AuthenticationAPI;
import com.api.employee_management.ui.employee.EmployeePage;
import net.miginfocom.swing.MigLayout;


public class LoginPage extends JPanel {
	private JTextField usernameField;
	private JPasswordField passwordField;
	public static String authToken; // Static to store the token for the session

	public LoginPage(CardLayout cardLayout, JPanel cardPanel) {
		setLayout(new MigLayout("", "[grow,fill]10[grow,fill]", "[]20[]20[]20[]"));

		// Title
		JLabel titleLabel = new JLabel("Employee Management System", JLabel.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
		titleLabel.setForeground(new Color(34, 153, 255)); // Blue color for title
		add(titleLabel, "span, wrap");

		// Username field
		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		usernameLabel.setForeground(Color.DARK_GRAY);
		usernameField = new JTextField(20);
		usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
		usernameField.setBorder(BorderFactory.createLineBorder(new Color(34, 153, 255), 2));

		// Password field
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		passwordLabel.setForeground(Color.DARK_GRAY);
		passwordField = new JPasswordField(20);
		passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
		passwordField.setBorder(BorderFactory.createLineBorder(new Color(34, 153, 255), 2));

		// Login button
		JButton loginButton = new JButton("Login");
		loginButton.setFont(new Font("Arial", Font.BOLD, 16));
		loginButton.setBackground(new Color(34, 153, 255));
		loginButton.setForeground(Color.WHITE);
		loginButton.setFocusPainted(false);
		loginButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

		// Login button action
		loginButton.addActionListener(e -> {
			String username = usernameField.getText();
			String password = new String(passwordField.getPassword());

			authToken = AuthenticationAPI.authenticateUser(username, password); // Store token
			if (authToken != null) {
				EmployeePage employeePage = new EmployeePage(cardLayout, cardPanel);
				cardPanel.add(employeePage, "EmployeePage");
				cardLayout.show(cardPanel, "EmployeePage");
			} else {
				JOptionPane.showMessageDialog(LoginPage.this, "Invalid login credentials!");
			}
		});

		// Add components to layout
		add(usernameLabel);
		add(usernameField, "wrap");
		add(passwordLabel);
		add(passwordField, "wrap");
		add(new JLabel()); // Empty space
		add(loginButton, "span, grow");

		// Set background color and padding
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
	}
}