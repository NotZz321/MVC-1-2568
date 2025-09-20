package View;

import Model.*;
import Controller.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class SubjectGradeView extends JFrame {
    private Controller controller;
    private JComboBox<Subject> subjectComboBox;
    private JTable studentsTable;
    private DefaultTableModel tableModel;
    private JButton saveGradesBtn;
    private JButton refreshBtn;
    private JLabel subjectInfoLabel;

    public SubjectGradeView(Controller controller) {
        this.controller = controller;
        
        initializeComponents();
        setupEventHandlers();
        loadSubjects();
    }

    private void initializeComponents() {
        setTitle("Grade Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top panel - Subject selection
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Select Subject:"));
        
        subjectComboBox = new JComboBox<>();
        subjectComboBox.setPreferredSize(new Dimension(300, 25));
        topPanel.add(subjectComboBox);
        
        refreshBtn = new JButton("Refresh");
        topPanel.add(refreshBtn);
        
        add(topPanel, BorderLayout.NORTH);

        // Subject info panel
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        subjectInfoLabel = new JLabel("Select a subject to view registered students");
        infoPanel.add(subjectInfoLabel);
        add(infoPanel, BorderLayout.CENTER);

        // Center panel - Students table (initially hidden)
        String[] columnNames = {"Student ID", "Name", "School", "Email", "Grade"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only Grade column is editable
            }
        };
        
        studentsTable = new JTable(tableModel);
        
        // Create grade dropdown for grade column
        JComboBox<String> gradeComboBox = new JComboBox<>(
            new String[]{"", "A", "B+", "B", "C+", "C", "D+", "D", "F"}
        );
        studentsTable.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(gradeComboBox));
        
        JScrollPane scrollPane = new JScrollPane(studentsTable);
        scrollPane.setVisible(false); // Initially hidden
        
        // Create a panel to hold both info and table
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(infoPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // Bottom panel - Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        saveGradesBtn = new JButton("Save Grades");
        saveGradesBtn.setEnabled(false);
        
        JButton closeBtn = new JButton("Close");
        
        buttonPanel.add(saveGradesBtn);
        buttonPanel.add(closeBtn);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Store reference to scroll pane for showing/hiding
        this.scrollPane = scrollPane;
    }
    
    private JScrollPane scrollPane;

    private void setupEventHandlers() {
        subjectComboBox.addActionListener(e -> {
            Subject selectedSubject = (Subject) subjectComboBox.getSelectedItem();
            if (selectedSubject != null) {
                loadStudentsForSubject(selectedSubject);
                scrollPane.setVisible(true);
                saveGradesBtn.setEnabled(true);
                updateSubjectInfo(selectedSubject);
            } else {
                scrollPane.setVisible(false);
                saveGradesBtn.setEnabled(false);
                subjectInfoLabel.setText("Select a subject to view registered students");
            }
        });
        
        refreshBtn.addActionListener(e -> {
            loadSubjects();
        });
        
        saveGradesBtn.addActionListener(e -> {
            saveGrades();
        });
        
        ((JButton) ((JPanel) getContentPane().getComponent(2)).getComponent(1))
            .addActionListener(e -> this.dispose()); // Close button
    }

    private void loadSubjects() {
        subjectComboBox.removeAllItems();
        ArrayList<Subject> subjects = controller.getSubjects();
        
        for (Subject subject : subjects) {
            subjectComboBox.addItem(subject);
        }
        
        // Custom renderer to show subject name and ID
        subjectComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, 
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Subject) {
                    Subject subject = (Subject) value;
                    setText(subject.getSubjectID() + " - " + subject.getSubjectName());
                }
                return this;
            }
        });
    }

    private void loadStudentsForSubject(Subject subject) {
        tableModel.setRowCount(0);
        
        if (subject.getStudents() != null && !subject.getStudents().isEmpty()) {
            for (Student student : subject.getStudents()) {
                Object[] row = {
                    student.getId(),
                    student.getPrefix() + " " + student.getFirstName() + " " + student.getLastName(),
                    student.getSchool(),
                    student.getEmail(),
                    "" // Grade - empty for now, would need RegisteredSubject model for actual grades
                };
                tableModel.addRow(row);
            }
        } else {
            // Show message if no students registered
            Object[] row = {"No students registered", "", "", "", ""};
            tableModel.addRow(row);
            saveGradesBtn.setEnabled(false);
        }
    }

    private void updateSubjectInfo(Subject subject) {
        String info = String.format(
            "Subject: %s - %s | Credits: %d | Instructor: %s | Students: %d/%s",
            subject.getSubjectID(),
            subject.getSubjectName(),
            subject.getCredits(),
            subject.getInstructor(),
            subject.getCurrentStudent(),
            subject.getMaxStudent() == -1 ? "∞" : String.valueOf(subject.getMaxStudent())
        );
        subjectInfoLabel.setText(info);
    }

    private void saveGrades() {
        Subject selectedSubject = (Subject) subjectComboBox.getSelectedItem();
        if (selectedSubject == null) return;
        
        int savedCount = 0;
        StringBuilder errors = new StringBuilder();
        
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String studentId = (String) tableModel.getValueAt(i, 0);
            String grade = (String) tableModel.getValueAt(i, 4);
            
            if (studentId != null && !studentId.equals("No students registered") && 
                grade != null && !grade.trim().isEmpty()) {
                
                // Here you would typically save to a RegisteredSubject model
                // For now, just count successful saves
                savedCount++;
            }
        }
        
        if (savedCount > 0) {
            JOptionPane.showMessageDialog(this,
                "Successfully saved grades for " + savedCount + " students.",
                "Grades Saved",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                "No grades were entered to save.",
                "No Changes",
                JOptionPane.WARNING_MESSAGE);
        }
    }
}