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
                    return "Student has not completed the prerequisite subjects.";
                }
            }
        }

        subject.register(student);
        return "Registration successful.";

    }

    public boolean checkMinimumAge(LocalDate dateOfBirth) {
        LocalDate today = LocalDate.now(); 
        Period period = Period.between(dateOfBirth, today);
        return period.getYears() >= 15;
    }

    public Object handleButtonClick() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleButtonClick'");
    }
}
