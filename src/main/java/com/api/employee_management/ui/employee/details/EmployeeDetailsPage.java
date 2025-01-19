package com.api.employee_management.ui.employee.details;

import javax.swing.*;
import java.awt.*;
import com.api.employee_management.controllers.dtos.EmployeeResponseDto;
import com.api.employee_management.ui.api.EmployeeAPI;
import com.api.employee_management.ui.authentication.LoginPage;

public class EmployeeDetailsPage extends JPanel {
	private String employeeId;
	private JLabel nameLabel, jobTitleLabel, departmentLabel, statusLabel, hireDateLabel;

	public EmployeeDetailsPage(String employeeId) {
		this.employeeId = employeeId;
		setLayout(new BorderLayout());

		// Main Card Panel for Centering Content
		JPanel cardPanel = new JPanel();
		cardPanel.setLayout(new BorderLayout());
		cardPanel.setBackground(Color.WHITE);
		cardPanel.setBorder(BorderFactory.createLineBorder(new Color(34, 153, 255), 2, true));
		add(cardPanel, BorderLayout.CENTER);

		// Header Label
		JLabel employeeLabel = new JLabel("Employee Details", JLabel.CENTER);
		employeeLabel.setFont(new Font("Arial", Font.BOLD, 24));
		employeeLabel.setForeground(new Color(0, 51, 102));  // Dark Blue Color
		employeeLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
		cardPanel.add(employeeLabel, BorderLayout.NORTH);

		// Employee Info Panel with Card-like Design
		JPanel infoPanel = new JPanel(new GridLayout(6, 2, 10, 20));
		infoPanel.setBackground(new Color(245, 245, 245)); // Light Gray Background
		infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		nameLabel = new JLabel();
		jobTitleLabel = new JLabel();
		departmentLabel = new JLabel();
		statusLabel = new JLabel();
		hireDateLabel = new JLabel();

		// Styling labels
		JLabel fullNameLabel = new JLabel("Full Name:");
		fullNameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		fullNameLabel.setForeground(new Color(0, 51, 102));  // Dark Blue Color

		JLabel jobTitleTextLabel = new JLabel("Job Title:");
		jobTitleTextLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		jobTitleTextLabel.setForeground(new Color(0, 51, 102));  // Dark Blue Color

		JLabel departmentTextLabel = new JLabel("Department:");
		departmentTextLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		departmentTextLabel.setForeground(new Color(0, 51, 102));  // Dark Blue Color

		JLabel statusTextLabel = new JLabel("Status:");
		statusTextLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		statusTextLabel.setForeground(new Color(0, 51, 102));  // Dark Blue Color

		JLabel hireDateTextLabel = new JLabel("Hire Date:");
		hireDateTextLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		hireDateTextLabel.setForeground(new Color(0, 51, 102));  // Dark Blue Color

		// Add components to infoPanel
		infoPanel.add(fullNameLabel);
		infoPanel.add(nameLabel);
		infoPanel.add(jobTitleTextLabel);
		infoPanel.add(jobTitleLabel);
		infoPanel.add(departmentTextLabel);
		infoPanel.add(departmentLabel);
		infoPanel.add(statusTextLabel);
		infoPanel.add(statusLabel);
		infoPanel.add(hireDateTextLabel);
		infoPanel.add(hireDateLabel);

		cardPanel.add(infoPanel, BorderLayout.CENTER);

		// Back Button Styling
		JButton backButton = new JButton("Back");
		backButton.setFont(new Font("Arial", Font.BOLD, 14));
		backButton.setForeground(Color.WHITE);
		backButton.setBackground(new Color(0, 102, 204)); // Blue background
		backButton.setFocusPainted(false);
		backButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		backButton.addActionListener(e -> goBack());

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(245, 245, 245)); // Light Gray Background
		buttonPanel.add(backButton);
		cardPanel.add(buttonPanel, BorderLayout.SOUTH);

		// Load employee details
		fetchEmployeeDetails();
	}

	private void fetchEmployeeDetails() {
		try {
			EmployeeResponseDto employee = EmployeeAPI.getEmployeeById(employeeId, LoginPage.authToken);
			displayEmployeeDetails(employee);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error fetching employee details: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void displayEmployeeDetails(EmployeeResponseDto employee) {
		nameLabel.setText(employee.getFullName());
		jobTitleLabel.setText(employee.getJobTitle());
		departmentLabel.setText(employee.getDepartment());
		statusLabel.setText(employee.getEmploymentStatus());
		hireDateLabel.setText(employee.getHireDate());
	}

	private void goBack() {
		// Navigate back to EmployeePage
		CardLayout cardLayout = (CardLayout) getParent().getLayout();
		JPanel cardPanel = (JPanel) getParent();
		cardLayout.show(cardPanel, "EmployeePage");
	}
}
