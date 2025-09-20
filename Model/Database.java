package Model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Database {
    private ArrayList<Student> students;
    private ArrayList<Subject> subjects;

    public Database() {
        students = new ArrayList<>();
        subjects = new ArrayList<>();

        addStudent();
        addSubject();
        addStudentToSubject(students.get(0), subjects.get(1));
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    private void addStudent() {
        students.add(new Student("69012345", "Mr.", "John", "Doe", LocalDate.of(2000, 1, 1), "ABC University", "john.doe@example.com"));
    }

    private void addSubject() {
        subjects.add(new Subject("05501234", "Mathematics", 3, "Dr. Smith", null, 30, 0));
        subjects.add(new Subject("05501001", "Basic Programming", 3, "Dr. Somchai", null, 50, 45));
    }

    private void addStudentToSubject(Student student, Subject subject) {
        subject.register(student);
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public Student findStudentByID(String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) return student;
        }
        return null;
    }

    public Subject findSubjectByID(String id) {
        for (Subject subject : subjects) {
            if (subject.getSubjectID().equals(id)) return subject;
        }
        return null;
    }
}
