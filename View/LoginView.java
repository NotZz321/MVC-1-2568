package View;

import Controller.*;
import Model.*;
import java.awt.*;
import javax.swing.*;

public class LoginView extends JFrame {
    public JTextField usernameField;
    public JPasswordField passwordField;
    public JButton loginBtn;
    // private Database database;
    private Controller controller;

    public LoginView(Database database, Controller controller) {
        // this.database = database;
        this.controller = controller;
        
        initializeComponents();
        setupEventHandlers();
    }
    
    private void initializeComponents() {
        setTitle("Student Registration System - Login");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // set to center of screen
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Title panel
        JPanel titlePanel = new JPanel(new FlowLayout());
        JLabel titleLabel = new JLabel("Student Registration System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Login form panel
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Username field
        formPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        formPanel.add(usernameField);

        // Password field
        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        // Login button
        loginBtn = new JButton("Login");
        formPanel.add(new JLabel());
        formPanel.add(loginBtn);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Instructions panel
        JPanel instructionsPanel = new JPanel(new FlowLayout());
        JLabel instructionsLabel = new JLabel("<html><center>Admin: username='admin', password='admin'<br>Student: username=StudentID</center></html>");
        instructionsLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        instructionsLabel.setForeground(Color.GRAY);
        instructionsPanel.add(instructionsLabel);
        mainPanel.add(instructionsPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
        
        // Set focus to username field
        usernameField.requestFocus();
    }
    
    private void setupEventHandlers() {
        // Enter key support
        getRootPane().setDefaultButton(loginBtn);
        
        loginBtn.addActionListener(e -> handleLogin());
        
        // Allow Enter key in password field
        passwordField.addActionListener(e -> handleLogin());
    }
    
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        // Check for empty username
        if (username.isEmpty()) {
            showError("Please enter a username.");
            return;
        }
        
        // Check for admin login
        if (username.equals("admin") && password.equals("admin")) {
            // Admin login successful
            try {
                // Open admin grade view
                AdminGradeView adminView = new AdminGradeView(controller.getStudents(), controller);
                adminView.setVisible(true);
                this.dispose();
            } catch (Exception ex) {
                showError("Error opening admin view: " + ex.getMessage());
            }
        } else {
            // Try student login (username should be student ID)
            Student student = controller.authenticateStudent(username);
            if (student != null) {
                // Student login successful
                try {
                    // Open student profile view
                    StudentProfileView profileView = new StudentProfileView(student, controller);
                    profileView.setVisible(true);
                    this.dispose();
                } catch (Exception ex) {
                    showError("Error opening student profile: " + ex.getMessage());
                }
            } else {
                showError("Invalid credentials. Please check your username and try again.");
            }
        }
        
        // Clear password field for security
        passwordField.setText("");
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Login Error", JOptionPane.ERROR_MESSAGE);
    }
}