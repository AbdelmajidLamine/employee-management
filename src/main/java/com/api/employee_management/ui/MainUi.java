package com.api.employee_management.ui;

import java.awt.*;
import javax.swing.*;

import com.api.employee_management.ui.authentication.LoginPage;
import com.api.employee_management.ui.employee.EmployeePage;

public class MainUi {
	private static CardLayout cardLayout = new CardLayout();
	private static JPanel cardPanel = new JPanel(cardLayout);

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Employee Management System");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(800, 600);

			LoginPage loginPage = new LoginPage(cardLayout, cardPanel);

			cardPanel.add(loginPage, "Login");
			frame.add(cardPanel);
			cardLayout.show(cardPanel, "Login");

			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}
}

