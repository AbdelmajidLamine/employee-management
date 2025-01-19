package com.api.employee_management.ui.employee;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

import com.api.employee_management.controllers.dtos.EmployeeRequestDto;
import com.api.employee_management.controllers.dtos.EmployeeResponseDto;
import com.api.employee_management.ui.employee.form.EmployeeForm;
import com.api.employee_management.ui.api.EmployeeAPI;
import com.api.employee_management.ui.authentication.LoginPage;
import com.api.employee_management.ui.employee.details.EmployeeDetailsPage;

public class EmployeePage extends JPanel {
	private JTable employeeTable;
	private DefaultTableModel tableModel;
	private JTextField searchField;
	private JComboBox<String> filterComboBox;
	private JButton searchButton, addEmployeeButton, updateEmployeeButton, deleteEmployeeButton, detailsButton;
	private CardLayout cardLayout;
	private JPanel cardPanel;

	public EmployeePage(CardLayout cardLayout, JPanel cardPanel) {
		this.cardLayout = cardLayout;
		this.cardPanel = cardPanel;

		setLayout(new BorderLayout(20, 20));
		setBackground(Color.WHITE);

		// Title Section
		JLabel employeeLabel = new JLabel("Employee Management", JLabel.CENTER);
		employeeLabel.setFont(new Font("Arial", Font.BOLD, 30));
		employeeLabel.setForeground(new Color(34, 153, 255)); // Blue color for title
		add(employeeLabel, BorderLayout.NORTH);

		// Employee Table
		String[] columns = {"ID", "Full Name", "Job Title", "Department", "Status", "Hire Date"};
		tableModel = new DefaultTableModel(columns, 0);
		employeeTable = new JTable(tableModel);
		employeeTable.setFont(new Font("Arial", Font.PLAIN, 14));
		employeeTable.setRowHeight(30);
		employeeTable.setSelectionBackground(new Color(255, 215, 0)); // Gold color for selected row
		employeeTable.setSelectionForeground(Color.BLACK);
		JScrollPane scrollPane = new JScrollPane(employeeTable);
		add(scrollPane, BorderLayout.CENTER);

		// Search and Filter Panel
		JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		searchPanel.setBackground(Color.WHITE);

		searchField = new JTextField(15);
		searchButton = new JButton("Search");
		searchButton.setBackground(new Color(34, 153, 255));
		searchButton.setForeground(Color.WHITE);
		searchButton.setFocusPainted(false);

		filterComboBox = new JComboBox<>(new String[]{"All", "Active", "Inactive"});
		searchPanel.add(new JLabel("Search:"));
		searchPanel.add(searchField);
		searchPanel.add(searchButton);
		searchPanel.add(new JLabel("Status:"));
		searchPanel.add(filterComboBox);
		add(searchPanel, BorderLayout.NORTH);

		// Buttons Panel
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		buttonsPanel.setBackground(Color.WHITE);

		addEmployeeButton = new JButton("Add Employee");
		updateEmployeeButton = new JButton("Update Employee");
		deleteEmployeeButton = new JButton("Delete Employee");
		detailsButton = new JButton("Details");

		addEmployeeButton.setBackground(new Color(34, 153, 255));
		updateEmployeeButton.setBackground(new Color(255, 165, 0)); // Orange color
		deleteEmployeeButton.setBackground(new Color(255, 0, 0)); // Red color
		detailsButton.setBackground(new Color(0, 204, 204)); // Teal color

		addEmployeeButton.setForeground(Color.WHITE);
		updateEmployeeButton.setForeground(Color.WHITE);
		deleteEmployeeButton.setForeground(Color.WHITE);
		detailsButton.setForeground(Color.WHITE);

		addEmployeeButton.setFocusPainted(false);
		updateEmployeeButton.setFocusPainted(false);
		deleteEmployeeButton.setFocusPainted(false);
		detailsButton.setFocusPainted(false);

		buttonsPanel.add(addEmployeeButton);
		buttonsPanel.add(updateEmployeeButton);
		buttonsPanel.add(deleteEmployeeButton);
		buttonsPanel.add(detailsButton);

		// Logout Button
		JButton logoutButton = new JButton("Logout");
		logoutButton.setBackground(new Color(255, 69, 0)); // Red-Orange color
		logoutButton.setForeground(Color.WHITE);
		logoutButton.setFocusPainted(false);
		logoutButton.addActionListener(e -> cardLayout.show(cardPanel, "Login"));
		buttonsPanel.add(logoutButton);

		add(buttonsPanel, BorderLayout.SOUTH);

		// Event Handlers
		searchButton.addActionListener(e -> searchEmployees());
		addEmployeeButton.addActionListener(e -> addEmployee());
		updateEmployeeButton.addActionListener(e -> updateEmployee());
		deleteEmployeeButton.addActionListener(e -> deleteEmployee());
		detailsButton.addActionListener(e -> openEmployeeDetails());

		// Initial Load
		fetchEmployees();
	}

	private void fetchEmployees() {
		try {
			List<EmployeeResponseDto> employees = EmployeeAPI.searchEmployees(new EmployeeRequestDto(), LoginPage.authToken);
			updateTable(employees);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error fetching employees: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void searchEmployees() {
		String searchQuery = searchField.getText();
		String statusFilter = (String) filterComboBox.getSelectedItem();

		try {
			EmployeeRequestDto requestDto = new EmployeeRequestDto();
			requestDto.setFullName(searchQuery);
			requestDto.setEmploymentStatus(statusFilter.equals("All") ? null : statusFilter);

			List<EmployeeResponseDto> employees = EmployeeAPI.searchEmployees(requestDto, LoginPage.authToken);
			updateTable(employees);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error searching employees: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void addEmployee() {
		EmployeeForm form = new EmployeeForm(new JFrame(), null, false);
		form.setVisible(true);

		EmployeeRequestDto newEmployee = form.getEmployee();
		if (newEmployee != null) {
			try {
				EmployeeAPI.addEmployee(newEmployee, LoginPage.authToken);
				fetchEmployees();
				JOptionPane.showMessageDialog(this, "Employee added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Error adding employee: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void updateEmployee() {
		int selectedRow = employeeTable.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Please select an employee to update.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		EmployeeRequestDto selectedEmployee = getSelectedEmployee();
		EmployeeForm form = new EmployeeForm(new JFrame(), selectedEmployee, true);
		form.setVisible(true);

		EmployeeRequestDto updatedEmployee = form.getEmployee();
		if (updatedEmployee != null) {
			try {
				EmployeeAPI.updateEmployee(updatedEmployee, LoginPage.authToken);
				fetchEmployees();
				JOptionPane.showMessageDialog(this, "Employee updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Error updating employee: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void deleteEmployee() {
		int selectedRow = employeeTable.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Please select an employee to delete.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		EmployeeRequestDto selectedEmployee = getSelectedEmployee();
		int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this employee?", "Confirm Delete", JOptionPane.YES_NO_OPTION);

		if (confirmation == JOptionPane.YES_OPTION) {
			try {
				EmployeeAPI.deleteEmployee(selectedEmployee.getEmployeeId(), LoginPage.authToken);
				fetchEmployees();
				JOptionPane.showMessageDialog(this, "Employee deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Error deleting employee: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void openEmployeeDetails() {
		int selectedRow = employeeTable.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Please select an employee to view details.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		String employeeId = employeeTable.getValueAt(selectedRow, 0).toString();

		EmployeeDetailsPage detailsPage = new EmployeeDetailsPage(employeeId);
		cardPanel.add(detailsPage, "EmployeeDetails");
		cardLayout.show(cardPanel, "EmployeeDetails");
	}

	private EmployeeRequestDto getSelectedEmployee() {
		int selectedRow = employeeTable.getSelectedRow();
		String id = employeeTable.getValueAt(selectedRow, 0).toString();
		String name = employeeTable.getValueAt(selectedRow, 1).toString();
		String jobTitle = employeeTable.getValueAt(selectedRow, 2).toString();
		String department = employeeTable.getValueAt(selectedRow, 3).toString();
		String status = employeeTable.getValueAt(selectedRow, 4).toString();
		String hireDate = employeeTable.getValueAt(selectedRow, 5).toString();

		return new EmployeeRequestDto(id, name, jobTitle, department, status, hireDate);
	}

	private void updateTable(List<EmployeeResponseDto> employees) {
		tableModel.setRowCount(0);
		for (EmployeeResponseDto employee : employees) {
			tableModel.addRow(new Object[]{
					employee.getEmployeeId(),
					employee.getFullName(),
					employee.getJobTitle(),
					employee.getDepartment(),
					employee.getEmploymentStatus(),
					employee.getHireDate()
			});
		}
	}
}
