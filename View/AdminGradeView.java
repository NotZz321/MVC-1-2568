package View;

import Model.*;
import Controller.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminGradeView extends JFrame {
    private JTable studentTable;
    private JTextField searchField;
    private JComboBox<String> schoolFilter;
    private JComboBox<String> sortOption;
    private JButton searchBtn;
    private JButton viewProfileBtn;
    private JButton gradeSubjectBtn;
    private DefaultTableModel tableModel;
    private ArrayList<Student> students;
    private ArrayList<Student> filteredStudents;
    private Controller controller;

    public AdminGradeView(ArrayList<Student> students, Controller controller) {
        this.students = students;
        this.controller = controller;
        this.filteredStudents = new ArrayList<>(students);
        
        initializeComponents();
        setupEventHandlers();
        loadStudentData();
    }

    private void initializeComponents() {
        setTitle("Admin - Student Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top panel - Search and filter controls
        JPanel topPanel = new JPanel(new FlowLayout());
        
        topPanel.add(new JLabel("Search:"));
        searchField = new JTextField(15);
        topPanel.add(searchField);
        
        searchBtn = new JButton("Search");
        topPanel.add(searchBtn);
        
        topPanel.add(new JLabel("School:"));
        schoolFilter = new JComboBox<>();
        schoolFilter.addItem("All Schools");
        populateSchoolFilter();
        topPanel.add(schoolFilter);
        
        topPanel.add(new JLabel("Sort by:"));
        sortOption = new JComboBox<>(new String[]{"Name", "Age", "ID"});
        topPanel.add(sortOption);

        add(topPanel, BorderLayout.NORTH);

        // Center panel - Student table
        String[] columnNames = {"Student ID", "Name", "School", "Email", "Age"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        studentTable = new JTable(tableModel);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom panel - Action buttons
        JPanel bottomPanel = new JPanel(new FlowLayout());
        viewProfileBtn = new JButton("View Student Profile");
        gradeSubjectBtn = new JButton("Grade Subject");
        
        bottomPanel.add(viewProfileBtn);
        bottomPanel.add(gradeSubjectBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void populateSchoolFilter() {
        java.util.Set<String> schools = new java.util.HashSet<>();
        for (Student student : students) {
            if (student.getSchool() != null) {
                schools.add(student.getSchool());
            }
        }
        for (String school : schools) {
            schoolFilter.addItem(school);
        }
    }

    private void setupEventHandlers() {
        searchBtn.addActionListener(e -> filterAndSortStudents());
        
        schoolFilter.addActionListener(e -> filterAndSortStudents());
        
        sortOption.addActionListener(e -> filterAndSortStudents());
        
        viewProfileBtn.addActionListener(e -> {
            int selectedRow = studentTable.getSelectedRow();
            if (selectedRow >= 0) {
                Student selectedStudent = filteredStudents.get(selectedRow);
                StudentProfileView profileView = new StudentProfileView(selectedStudent, controller);
                profileView.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a student first.");
            }
        });
        
        gradeSubjectBtn.addActionListener(e -> {
            SubjectGradeView gradeView = new SubjectGradeView(controller);
            gradeView.setVisible(true);
        });
    }

    private void filterAndSortStudents() {
        String searchText = searchField.getText().toLowerCase();
        String selectedSchool = (String) schoolFilter.getSelectedItem();
        String sortBy = (String) sortOption.getSelectedItem();

        // Filter students
        filteredStudents.clear();
        for (Student student : students) {
            boolean matchesSearch = searchText.isEmpty() || 
                                  student.getFirstName().toLowerCase().contains(searchText) ||
                                  student.getLastName().toLowerCase().contains(searchText) ||
                                  student.getId().toLowerCase().contains(searchText);
            
            boolean matchesSchool = "All Schools".equals(selectedSchool) || 
                                  selectedSchool.equals(student.getSchool());
            
            if (matchesSearch && matchesSchool) {
                filteredStudents.add(student);
            }
        }

        // Sort students
        filteredStudents.sort((s1, s2) -> {
            switch (sortBy) {
                case "Name":
                    return (s1.getFirstName() + " " + s1.getLastName())
                           .compareTo(s2.getFirstName() + " " + s2.getLastName());
                case "Age":
                    if (s1.getDateOfBirth() == null && s2.getDateOfBirth() == null) return 0;
                    if (s1.getDateOfBirth() == null) return 1;
                    if (s2.getDateOfBirth() == null) return -1;
                    return s1.getDateOfBirth().compareTo(s2.getDateOfBirth());
                case "ID":
                default:
                    return s1.getId().compareTo(s2.getId());
            }
        });

        loadStudentData();
    }

    private void loadStudentData() {
        tableModel.setRowCount(0);
        for (Student student : filteredStudents) {
            Object[] row = {
                student.getId(),
                student.getPrefix() + " " + student.getFirstName() + " " + student.getLastName(),
                student.getSchool(),
                student.getEmail(),
                calculateAge(student)
            };
            tableModel.addRow(row);
        }
    }

    private String calculateAge(Student student) {
        if (student.getDateOfBirth() == null) return "N/A";
        
        java.time.Period period = java.time.Period.between(
            student.getDateOfBirth(), 
            java.time.LocalDate.now()
        );
        return String.valueOf(period.getYears());
    }
}