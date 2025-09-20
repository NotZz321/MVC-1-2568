package View;

import Controller.*;
import Model.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SubjectListView extends JFrame {
    private Controller controller;
    private Student student;
    private JTable subjectTable;
    private DefaultTableModel tableModel;
    public JButton registerBtn;
    private JButton cancelBtn;
    private JButton viewDetailsBtn;
    private ArrayList<Subject> availableSubjects;

    public SubjectListView(Controller controller, Student student) {
        this.controller = controller;
        this.student = student;
        this.availableSubjects = new ArrayList<>();
        
        initializeComponents();
        setupEventHandlers();
        loadAvailableSubjects();
    }

    private void initializeComponents() {
        setTitle("Available Subjects for Registration");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top panel - Information
        JPanel infoPanel = new JPanel(new FlowLayout());
        infoPanel.add(new JLabel("Student: " + student.getFirstName() + " " + student.getLastName() + 
                                " (ID: " + student.getId() + ")"));
        add(infoPanel, BorderLayout.NORTH);

        // Center panel - Subject table
        String[] columnNames = {"Subject ID", "Subject Name", "Credits", "Instructor", 
                               "Max Students", "Current Students", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        subjectTable = new JTable(tableModel);
        subjectTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        subjectTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateButtonStates();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(subjectTable);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom panel - Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        registerBtn = new JButton("Register");
        viewDetailsBtn = new JButton("View Details");
        cancelBtn = new JButton("Cancel");
        
        buttonPanel.add(registerBtn);
        buttonPanel.add(viewDetailsBtn);
        buttonPanel.add(cancelBtn);
        add(buttonPanel, BorderLayout.SOUTH);
        
        updateButtonStates();
    }

    private void setupEventHandlers() {
        registerBtn.addActionListener(e -> {
            int selectedRow = subjectTable.getSelectedRow();
            if (selectedRow >= 0) {
                Subject selectedSubject = availableSubjects.get(selectedRow);
                registerForSubject(selectedSubject);
            }
        });
        
        viewDetailsBtn.addActionListener(e -> {
            int selectedRow = subjectTable.getSelectedRow();
            if (selectedRow >= 0) {
                Subject selectedSubject = availableSubjects.get(selectedRow);
                showSubjectDetails(selectedSubject);
            }
        });
        
        cancelBtn.addActionListener(e -> {
            this.dispose();
        });
    }

    private void loadAvailableSubjects() {
        availableSubjects.clear();
        tableModel.setRowCount(0);
        
        ArrayList<Subject> allSubjects = controller.getSubjects();
        
        for (Subject subject : allSubjects) {
            // Only show subjects the student is not already registered for
            if (!subject.isStudentRegistered(student.getId())) {
                availableSubjects.add(subject);
                
                String status = getRegistrationStatus(subject);
                String maxStudents = subject.getMaxStudent() == -1 ? "Unlimited" : 
                                   String.valueOf(subject.getMaxStudent());
                
                Object[] row = {
                    subject.getSubjectID(),
                    subject.getSubjectName(),
                    subject.getCredits(),
                    subject.getInstructor(),
                    maxStudents,
                    subject.getCurrentStudent(),
                    status
                };
                tableModel.addRow(row);
            }
        }
    }

    private String getRegistrationStatus(Subject subject) {
        // Check age requirement
        if (!controller.checkMinimumAge(student.getDateOfBirth())) {
            return "Age < 15";
        }
        
        // Check if subject is full
        if (!subject.canRegister()) {
            return "Full";
        }
        
        // Check prerequisites
        if (subject.getPreSubjectID() != null) {
            for (Subject preSubject : subject.getPreSubjectID()) {
                if (!preSubject.isStudentRegistered(student.getId())) {
                    return "Have To Register " + preSubject.getSubjectName();
                }
            }
        }
        
        return "Available";
    }

    private void registerForSubject(Subject subject) {
        String result = controller.register(student.getId(), subject.getSubjectID());
        
        if ("Registration successful.".equals(result)) {
            JOptionPane.showMessageDialog(this, 
                "Successfully registered for " + subject.getSubjectName() + "!",
                "Registration Successful", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Refresh the subject list
            loadAvailableSubjects();
            
            // Optionally close this window and return to profile
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Registration failed: " + result,
                "Registration Failed", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showSubjectDetails(Subject subject) {
        SubjectDetailView detailView = new SubjectDetailView(subject);
        detailView.setVisible(true);
    }

    private void updateButtonStates() {
        boolean hasSelection = subjectTable.getSelectedRow() >= 0;
        registerBtn.setEnabled(hasSelection);
        viewDetailsBtn.setEnabled(hasSelection);
        
        if (hasSelection) {
            int selectedRow = subjectTable.getSelectedRow();
            Subject selectedSubject = availableSubjects.get(selectedRow);
            String status = getRegistrationStatus(selectedSubject);
            registerBtn.setEnabled("Available".equals(status));
        }
    }
}