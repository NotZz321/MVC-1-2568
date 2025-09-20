package Model;

import java.util.ArrayList;

public class Database {
    private ArrayList<Student> students;
    private ArrayList<Subject> subjects;

    public Database() {
        students = new ArrayList<>();
        subjects = new ArrayList<>();

        addStudent();
        addSubject();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    private void addStudent() {
        students.add(new Student("69012345", "Mr.", "John", "Doe", null, "ABC University", "john.doe@example.com"));
    }

    private void addSubject() {
        subjects.add(new Subject("05501234", "Mathematics", 3, "Dr. Smith", null, 30, 0));
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
