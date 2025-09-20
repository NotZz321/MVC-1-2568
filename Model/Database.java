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

        // Example: pre-register a student to a subject
        addStudentToSubject(students.get(0), subjects.get(1));
        addStudentToSubject(students.get(2), subjects.get(6));
        addStudentToSubject(students.get(3), subjects.get(6));
        addStudentToSubject(students.get(7), subjects.get(8));
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    private void addStudent() {
        students.add(new Student("69000001", "Mr.", "John", "Doe", LocalDate.of(2000, 1, 1), "KMITL", "john.doe@example.com"));
        students.add(new Student("69000002", "Mr.", "Jane", "Smith", LocalDate.of(2020, 2, 2), "KMITL", "jane.smith@example.com"));
        students.add(new Student("69000003", "Mrs.", "Bam", "Cute", LocalDate.of(2015, 9, 20), "KDAI", "bam.cute@example.com"));
        students.add(new Student("69000004", "Mrs.", "Navia", "Blackrose", LocalDate.of(2013, 5, 16), "KDAI", "navia@example.com"));
        students.add(new Student("69000005", "Mr.", "Tim", "Lovechild", LocalDate.of(2010, 8, 30), "TNI", "TimVanguard@example.com"));
        students.add(new Student("69000006", "Mrs.", "Barbara", "Singer", LocalDate.of(2018, 1, 1), "KMITL", "barbaraIdol@example.com"));
        students.add(new Student("69000007", "Mr.", "First", "FLin", LocalDate.of(2012, 10, 5), "TNI", "FLinGaming@example.com"));
        students.add(new Student("69000008", "Mr.", "Ohm", "Bedroom", LocalDate.of(2002, 12, 2), "KMITL", "OhmLoveBedroom@example.com"));
        students.add(new Student("69000009", "Mrs.", "Cyrene", "Elf", LocalDate.of(2017, 9, 8), "Realme", "MissPinkElf@example.com"));
        students.add(new Student("69000010", "Mrs.", "Pream", "VTube", LocalDate.of(2004, 9, 9), "KMITL", "PreamSakura@example.com"));
        students.add(new Student("69000011", "Mrs.", "Elysia", "Elf", LocalDate.of(2007, 8, 9), "Realme", "SuperMissPinkElf@example.com"));
    }

    private void addSubject() {
        subjects.add(new Subject("05501234", "Mathematics", 3, "Dr. Pantanee", null, 30, 0));
        subjects.add(new Subject("05501001", "Programming Fundamental", 3, "Dr. Santana", null, -1, 0));
        
        // presubject is Programming Fundamental
        subjects.add(new Subject("05501002", "Object Oriented Programming", 3, "Dr. Santana", subjects.get(1), -1, 0));
        subjects.add(new Subject("05501003", "Data Structures", 3, "Dr. Jeeraporn", subjects.get(2), 30, 30));
        subjects.add(new Subject("05501004", "Statistics", 3, "Dr. Pantanee", null, 10, 0));

        // presubject is mathematics
        subjects.add(new Subject("05501005", "Discrete Mathematics", 3, "Dr. Visan", subjects.get(0), 20, 0));
        subjects.add(new Subject("90691234", "English", 3, "Dr. Ohm", null, 25, 0));
        subjects.add(new Subject("90691243", "Japanese", 3, "Dr. Perl", null, 5, 3));
        subjects.add(new Subject("90691259", "Fun with Airplane", 3, "Dr. !CE", null, 7, 6));
        subjects.add(new Subject("90691534", "Chess", 3, "Dr. Ohm", null, 50, 28));
        subjects.add(new Subject("90696666", "Game Caster", 3, "Dr. Not", null, 10, 5));
        subjects.add(new Subject("90691593", "How to Sleep", 3, "Dr. Ohm", null, 60, 40));
        subjects.add(new Subject("90691759", "Fun with Discord", 3, "Dr. Hon", null, 3, 1));
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
