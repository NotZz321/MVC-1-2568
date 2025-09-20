package Controller;

import Model.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class Controller {
    private Database database;
    private ArrayList<Student> students;
    private ArrayList<Subject> subjects;

    public Controller(Database database) {
        this.database = database;
        this.students = database.getStudents();
        this.subjects = database.getSubjects();
    }

    public String register(String studentID, String subjectID) {
        Student student = database.findStudentByID(studentID);
        Subject subject = database.findSubjectByID(subjectID);

        if (student == null) {
            return "Student not found.";
        }

        if (!checkMinimumAge(student.getDateOfBirth())) {
            return "Student age below than 15 year";
        }

        if (subject == null) {
            return "Subject not found.";
        }

        if (!subject.canRegister()) {
            return "Subject is full.";
        }

        if (subject.isStudentRegistered(student.getId())) {
            return "Student is already registered for this subject.";
        }

        if (subject.getPreSubjectID() != null) {
            for (Subject preSubject : subject.getPreSubjectID()) {
                if (!preSubject.isStudentRegistered(student.getId())) {
                    return "Student has not completed the presubjects.";
                }
            }
        }

        subject.register(student);
        return "Registration successful.";
    }

    public boolean checkMinimumAge(LocalDate dateOfBirth) {
        if (dateOfBirth == null) return false;
        LocalDate today = LocalDate.now(); 
        Period period = Period.between(dateOfBirth, today);
        return period.getYears() >= 15;
    }

    public String calculateAgeForShow(Student student) {
        if (student.getDateOfBirth() == null) return "N/A";
        
        java.time.Period period = java.time.Period.between(
            student.getDateOfBirth(), 
            java.time.LocalDate.now()
        );
        return String.valueOf(period.getYears()) + " years old";
    }

    public boolean authenticateUser(String username, String password) {
        return username.equals("admin") && password.equals("admin");
    }

    public Student authenticateStudent(String studentID) {
        return database.findStudentByID(studentID);
    }

    public Database getDatabase() {
        return database;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }
}