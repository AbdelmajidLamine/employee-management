package com.api.employee_management.ui.employee.form;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.api.employee_management.controllers.dtos.EmployeeRequestDto;

public class EmployeeForm extends JDialog {
	private JTextField employeeIdField, nameField, jobTitleField, departmentField, hireDateField, contactInfoField, addressField;
	private JComboBox<String> statusComboBox;
	private boolean isEditMode;
	private EmployeeRequestDto employee;

	public EmployeeForm(JFrame parent, EmployeeRequestDto employee, boolean isEditMode) {
		super(parent, isEditMode ? "Edit Employee" : "Add Employee", true);
		this.isEditMode = isEditMode;
		this.employee = employee;

		setLayout(new MigLayout("", "[grow,fill]10[grow,fill]", "[]20[]20[]20[]20[]20[]20[]"));
		setSize(500, 500);  // Increased size to accommodate new fields
		setLocationRelativeTo(parent);

		// Title
		JLabel titleLabel = new JLabel(isEditMode ? "Edit Employee" : "Add Employee", JLabel.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
		titleLabel.setForeground(new Color(34, 153, 255)); // Blue color for title
		add(titleLabel, "span, wrap");

		// Employee ID
		JLabel employeeIdLabel = new JLabel("Employee ID:");
		employeeIdLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		employeeIdLabel.setForeground(Color.DARK_GRAY);
		employeeIdField = new JTextField(20);
		employeeIdField.setFont(new Font("Arial", Font.PLAIN, 14));
		employeeIdField.setBorder(BorderFactory.createLineBorder(new Color(34, 153, 255), 2));

		// Full Name
		JLabel nameLabel = new JLabel("Full Name:");
		nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		nameLabel.setForeground(Color.DARK_GRAY);
		nameField = new JTextField(20);
		nameField.setFont(new Font("Arial", Font.PLAIN, 14));
		nameField.setBorder(BorderFactory.createLineBorder(new Color(34, 153, 255), 2));

		// Job Title
		JLabel jobTitleLabel = new JLabel("Job Title:");
		jobTitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		jobTitleLabel.setForeground(Color.DARK_GRAY);
		jobTitleField = new JTextField(20);
		jobTitleField.setFont(new Font("Arial", Font.PLAIN, 14));
		jobTitleField.setBorder(BorderFactory.createLineBorder(new Color(34, 153, 255), 2));

		// Department
		JLabel departmentLabel = new JLabel("Department:");
		departmentLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		departmentLabel.setForeground(Color.DARK_GRAY);
		departmentField = new JTextField(20);
		departmentField.setFont(new Font("Arial", Font.PLAIN, 14));
		departmentField.setBorder(BorderFactory.createLineBorder(new Color(34, 153, 255), 2));

		// Status ComboBox
		JLabel statusLabel = new JLabel("Status:");
		statusLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		statusLabel.setForeground(Color.DARK_GRAY);
		statusComboBox = new JComboBox<>(new String[]{"Active", "Inactive"});
		statusComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
		statusComboBox.setBorder(BorderFactory.createLineBorder(new Color(34, 153, 255), 2));

		// Hire Date
		JLabel hireDateLabel = new JLabel("Hire Date (yyyy-MM-dd):");
		hireDateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		hireDateLabel.setForeground(Color.DARK_GRAY);
		hireDateField = new JTextField(20);
		hireDateField.setFont(new Font("Arial", Font.PLAIN, 14));
		hireDateField.setBorder(BorderFactory.createLineBorder(new Color(34, 153, 255), 2));

		// Contact Information
		JLabel contactInfoLabel = new JLabel("Contact Information:");
		contactInfoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		contactInfoLabel.setForeground(Color.DARK_GRAY);
		contactInfoField = new JTextField(20);
		contactInfoField.setFont(new Font("Arial", Font.PLAIN, 14));
		contactInfoField.setBorder(BorderFactory.createLineBorder(new Color(34, 153, 255), 2));

		// Address
		JLabel addressLabel = new JLabel("Address:");
		addressLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		addressLabel.setForeground(Color.DARK_GRAY);
		addressField = new JTextField(20);
		addressField.setFont(new Font("Arial", Font.PLAIN, 14));
		addressField.setBorder(BorderFactory.createLineBorder(new Color(34, 153, 255), 2));

		// Buttons
		JButton saveButton = new JButton(isEditMode ? "Update" : "Add");
		saveButton.setFont(new Font("Arial", Font.BOLD, 16));
		saveButton.setBackground(new Color(34, 153, 255));
		saveButton.setForeground(Color.WHITE);
		saveButton.setFocusPainted(false);
		saveButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		saveButton.addActionListener(e -> saveEmployee());

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setFont(new Font("Arial", Font.PLAIN, 16));
		cancelButton.setBackground(Color.RED);
		cancelButton.setForeground(Color.WHITE);
		cancelButton.setFocusPainted(false);
		cancelButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		cancelButton.addActionListener(e -> dispose());

		// Add components to layout
		add(employeeIdLabel);
		add(employeeIdField, "wrap");
		add(nameLabel);
		add(nameField, "wrap");
		add(jobTitleLabel);
		add(jobTitleField, "wrap");
		add(departmentLabel);
		add(departmentField, "wrap");
		add(statusLabel);
		add(statusComboBox, "wrap");
		add(hireDateLabel);
		add(hireDateField, "wrap");
		add(contactInfoLabel);
		add(contactInfoField, "wrap");
		add(addressLabel);
		add(addressField, "wrap");
		add(new JLabel()); // Empty space
		add(saveButton, "span, grow");
		add(cancelButton, "span, grow");

		// Set background color and padding
		setBackground(Color.WHITE);

		// Pre-fill fields if in edit mode
		if (isEditMode && employee != null) {
			populateFields();
		}
	}

	private void populateFields() {
		employeeIdField.setText(employee.getEmployeeId() != null ? employee.getEmployeeId().toString() : "");
		nameField.setText(employee.getFullName());
		jobTitleField.setText(employee.getJobTitle());
		departmentField.setText(employee.getDepartment());
		statusComboBox.setSelectedItem(employee.getEmploymentStatus());
		hireDateField.setText(employee.getHireDate());
		contactInfoField.setText(employee.getContactInfo());  // Assuming 'contactInfo' is available in the DTO
		addressField.setText(employee.getAddress());  // Assuming 'address' is available in the DTO
	}

	private void saveEmployee() {
		String employeeId = employeeIdField.getText().trim();
		String name = nameField.getText().trim();
		String jobTitle = jobTitleField.getText().trim();
		String department = departmentField.getText().trim();
		String status = (String) statusComboBox.getSelectedItem();
		String hireDate = hireDateField.getText().trim();
		String contactInfo = contactInfoField.getText().trim();
		String address = addressField.getText().trim();

		if (name.isEmpty() || jobTitle.isEmpty() || department.isEmpty() || hireDate.isEmpty()) {
			JOptionPane.showMessageDialog(this, "All fields must be filled.", "Validation Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			LocalDate.parse(hireDate); // Validate hire date format
		} catch (DateTimeParseException e) {
			JOptionPane.showMessageDialog(this, "Hire Date must be in the format yyyy-MM-dd.", "Validation Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (isEditMode && employee != null) {
			// Update existing employee
			employee.setFullName(name);
			employee.setJobTitle(jobTitle);
			employee.setDepartment(department);
			employee.setEmploymentStatus(status);
			employee.setHireDate(hireDate);
			employee.setContactInfo(contactInfo);
			employee.setAddress(address);
		} else {
			// Create new employee
			employee = new EmployeeRequestDto(employeeId, name, jobTitle, department, status, hireDate, contactInfo, address);
		}

		dispose(); // Close the dialog
	}

	public EmployeeRequestDto getEmployee() {
		return employee;
	}
}
