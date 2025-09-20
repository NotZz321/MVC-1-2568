package Model;

import java.util.ArrayList;

public class Subject {
    private String subjectID;
    private String subjectName;
    private int credits;
    private String instructor;
    private ArrayList<Subject> preSubjectID;
    private int maxStudent;
    private int currentStudent;
    private ArrayList<Student> students; // แก้ไข: เพิ่ม initialization

    public Subject(String subjectID, String subjectName, int credits, String instructor, 
                   ArrayList<Subject> preSubjectID, int maxStudent, int currentStudent) {
        if (isValidSubjectID(subjectID) && isValidCredits(credits) && isValidmaxStudent(maxStudent)) {
            this.subjectID = subjectID;
            this.subjectName = subjectName;
            this.credits = credits;
            this.instructor = instructor;
            this.preSubjectID = preSubjectID;
            this.maxStudent = maxStudent;
            this.currentStudent = currentStudent;
            this.students = new ArrayList<>(); // แก้ไข: initialize ArrayList
        } else {
            throw new IllegalArgumentException("Invalid Subject ID");
        }
    }

    private boolean isValidSubjectID(String id) {
        // subject id must start with "0550" or "9069" and be 8 characters long
        if (id.length() == 8 
        && (id.substring(0, 4).equals("0550") || (id.substring(0, 4).equals("9069")))) {
            return true;
        }
        return false;
    }

    private boolean isValidCredits(int credits) {
        return credits > 0;
    }

    private boolean isValidmaxStudent(int maxStudent) {
        return maxStudent > 0 || maxStudent == -1;
    }

    public boolean canRegister() {
        if (maxStudent == -1) {
            return true;
        } else if (currentStudent < maxStudent) {
            return true;
        }
        return false;
    }

    public void register(Student student) {
        if (canRegister()) {
            students.add(student);
            currentStudent++;
        } else {
            throw new IllegalStateException("Subject is full");
        }
    }

    public boolean isStudentRegistered(String studentID) {
        if (students == null) return false; // แก้ไข: เพิ่ม null check
        for (Student student : students) {
            if (student.getId().equals(studentID)) {
                return true;
            }
        }
        return false;
    }

    // Getters
    public String getSubjectID() { return subjectID; }
    public String getSubjectName() { return subjectName; }
    public int getCredits() { return credits; }
    public String getInstructor() { return instructor; }
    public ArrayList<Subject> getPreSubjectID() { return preSubjectID; }
    public int getMaxStudent() { return maxStudent; }
    public int getCurrentStudent() { return currentStudent; }
    public ArrayList<Student> getStudents() { return students; }
}