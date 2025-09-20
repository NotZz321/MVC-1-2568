package View;

import Model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class SubjectDetailView extends JFrame {
    private Subject subject;
    private JLabel subjectIdLabel;
    private JLabel subjectNameLabel;
    private JLabel creditsLabel;
    private JLabel instructorLabel;
    private JLabel maxStudentsLabel;
    private JLabel currentStudentsLabel;
    private JTable prerequisitesTable;
    private JTable registeredStudentsTable;
    private DefaultTableModel prereqTableModel;
    private DefaultTableModel studentsTableModel;

    public SubjectDetailView(Subject subject) {
        this.subject = subject;
        
        initializeComponents();
        loadSubjectDetails();
    }

    private void initializeComponents() {
        setTitle("Subject Details");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Subject Information Tab
        JPanel infoPanel = createInfoPanel();
        tabbedPane.addTab("Subject Information", infoPanel);

        // Prerequisites Tab
        JPanel prereqPanel = createPrerequisitesPanel();
        tabbedPane.addTab("Prerequisites", prereqPanel);

        // Registered Students Tab
        JPanel studentsPanel = createRegisteredStudentsPanel();
        tabbedPane.addTab("Registered Students", studentsPanel);

        add(tabbedPane, BorderLayout.CENTER);

        // Close button
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> this.dispose());
        buttonPanel.add(closeBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Subject ID
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Subject ID:"), gbc);
        gbc.gridx = 1;
        subjectIdLabel = new JLabel();
        subjectIdLabel.setFont(subjectIdLabel.getFont().deriveFont(Font.BOLD));
        panel.add(subjectIdLabel, gbc);

        // Subject Name
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Subject Name:"), gbc);
        gbc.gridx = 1;
        subjectNameLabel = new JLabel();
        subjectNameLabel.setFont(subjectNameLabel.getFont().deriveFont(Font.BOLD));
        panel.add(subjectNameLabel, gbc);

        // Credits
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Credits:"), gbc);
        gbc.gridx = 1;
        creditsLabel = new JLabel();
        panel.add(creditsLabel, gbc);

        // Instructor
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Instructor:"), gbc);
        gbc.gridx = 1;
        instructorLabel = new JLabel();
        panel.add(instructorLabel, gbc);

        // Max Students
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Maximum Students:"), gbc);
        gbc.gridx = 1;
        maxStudentsLabel = new JLabel();
        panel.add(maxStudentsLabel, gbc);

        // Current Students
        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(new JLabel("Current Students:"), gbc);
        gbc.gridx = 1;
        currentStudentsLabel = new JLabel();
        panel.add(currentStudentsLabel, gbc);

        return panel;
    }

    private JPanel createPrerequisitesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columnNames = {"Subject ID", "Subject Name", "Credits", "Instructor"};
        prereqTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        prerequisitesTable = new JTable(prereqTableModel);
        JScrollPane scrollPane = new JScrollPane(prerequisitesTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createRegisteredStudentsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columnNames = {"Student ID", "Name", "School", "Email"};
        studentsTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        registeredStudentsTable = new JTable(studentsTableModel);
        JScrollPane scrollPane = new JScrollPane(registeredStudentsTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }

    private void loadSubjectDetails() {
        // Load basic information
        subjectIdLabel.setText(subject.getSubjectID());
        subjectNameLabel.setText(subject.getSubjectName());
        creditsLabel.setText(String.valueOf(subject.getCredits()));
        instructorLabel.setText(subject.getInstructor());
        
        String maxStudentsText = subject.getMaxStudent() == -1 ? "Unlimited" : 
                                String.valueOf(subject.getMaxStudent());
        maxStudentsLabel.setText(maxStudentsText);
        currentStudentsLabel.setText(String.valueOf(subject.getCurrentStudent()));

        // Load prerequisites
        loadPrerequisites();
        
        // Load registered students
        loadRegisteredStudents();
    }

    private void loadPrerequisites() {
        prereqTableModel.setRowCount(0);
        
        if (subject.getPreSubjectID() != null) {
            for (Subject prereq : subject.getPreSubjectID()) {
                Object[] row = {
                    prereq.getSubjectID(),
                    prereq.getSubjectName(),
                    prereq.getCredits(),
                    prereq.getInstructor()
                };
                prereqTableModel.addRow(row);
            }
        }
        
        if (prereqTableModel.getRowCount() == 0) {
            Object[] row = {"No prerequisites", "", "", ""};
            prereqTableModel.addRow(row);
        }
    }

    private void loadRegisteredStudents() {
        studentsTableModel.setRowCount(0);
        
        if (subject.getStudents() != null) {
            for (Student student : subject.getStudents()) {
                Object[] row = {
                    student.getId(),
                    student.getPrefix() + " " + student.getFirstName() + " " + student.getLastName(),
                    student.getSchool(),
                    student.getEmail()
                };
                studentsTableModel.addRow(row);
            }
        }
        
        if (studentsTableModel.getRowCount() == 0) {
            Object[] row = {"No students registered", "", "", ""};
            studentsTableModel.addRow(row);
        }
    }
}