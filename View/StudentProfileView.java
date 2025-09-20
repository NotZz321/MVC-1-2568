package View;

import Model.*;
import Controller.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class StudentProfileView extends JFrame {
    private Student student;
    private Controller controller;
    private JLabel nameLabel;
    private JLabel idLabel;
    private JLabel schoolLabel;
    private JLabel emailLabel;
    private JLabel ageLabel;
    private JTable registeredSubjectsTable;
    private DefaultTableModel tableModel;
    public JButton registerBtn;
    private JButton refreshBtn;

    public StudentProfileView(Student student, Controller controller) {
        this.student = student;
        this.controller = controller;
        
        initializeComponents();
        setupEventHandlers();
        updateProfile(student);
    }

    private void initializeComponents() {
        setTitle("Student Profile - " + student.getFirstName() + " " + student.getLastName());
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top panel - Student information
        JPanel infoPanel = new JPanel(new GridLayout(5, 2, 10, 5));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Student Information"));
        
        infoPanel.add(new JLabel("Student ID:"));
        idLabel = new JLabel();
        infoPanel.add(idLabel);
        
        infoPanel.add(new JLabel("Name:"));
        nameLabel = new JLabel();
        infoPanel.add(nameLabel);
        
        infoPanel.add(new JLabel("School:"));
        schoolLabel = new JLabel();
        infoPanel.add(schoolLabel);
        
        infoPanel.add(new JLabel("Email:"));
        emailLabel = new JLabel();
        infoPanel.add(emailLabel);
        
        infoPanel.add(new JLabel("Age:"));
        ageLabel = new JLabel();
        infoPanel.add(ageLabel);

        add(infoPanel, BorderLayout.NORTH);

        // Center panel - Registered subjects table
        JPanel subjectsPanel = new JPanel(new BorderLayout());
        subjectsPanel.setBorder(BorderFactory.createTitledBorder("Registered Subjects"));
        
        String[] columnNames = {"Subject ID", "Subject Name", "Credits", "Instructor", "Grade"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        registeredSubjectsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(registeredSubjectsTable);
        subjectsPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(subjectsPanel, BorderLayout.CENTER);

        // Bottom panel - Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        registerBtn = new JButton("Register for Subjects");
        refreshBtn = new JButton("Refresh");
        
        buttonPanel.add(registerBtn);
        buttonPanel.add(refreshBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupEventHandlers() {
        registerBtn.addActionListener(e -> {
            SubjectListView subjectListView = new SubjectListView(controller, student);
            subjectListView.setVisible(true);
        });
        
        refreshBtn.addActionListener(e -> {
            updateProfile(student);
        });
    }

    public void updateProfile(Student student) {
        this.student = student;
        
        // Update student information
        idLabel.setText(student.getId());
        nameLabel.setText(student.getPrefix() + " " + student.getFirstName() + " " + student.getLastName());
        schoolLabel.setText(student.getSchool());
        emailLabel.setText(student.getEmail());
        ageLabel.setText(calculateAge(student));
        
        // Update registered subjects table
        loadRegisteredSubjects();
    }

    private void loadRegisteredSubjects() {
        tableModel.setRowCount(0);
        
        // Get all subjects from database
        ArrayList<Subject> allSubjects = controller.getSubjects();
        
        for (Subject subject : allSubjects) {
            if (subject.isStudentRegistered(student.getId())) {
                Object[] row = {
                    subject.getSubjectID(),
                    subject.getSubjectName(),
                    subject.getCredits(),
                    subject.getInstructor(),
                    "N/A" // Grade - would need RegisteredSubject model to store actual grades
                };
                tableModel.addRow(row);
            }
        }
    }

    private String calculateAge(Student student) {
        if (student.getDateOfBirth() == null) return "N/A";
        
        java.time.Period period = java.time.Period.between(
            student.getDateOfBirth(), 
            java.time.LocalDate.now()
        );
        return String.valueOf(period.getYears()) + " years old";
    }
}