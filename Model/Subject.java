public class Subject {
    private String subjectID;
    private String subjectName;
    private int credits;
    private String instructor;
    private String preSubjectID;
    private String maxStudent;
    private String currentStudent;

    public Subject(String subjectID, String subjectName, int credits, String instructor, String preSubjectID, String maxStudent, String currentStudent) {
        if (isValidSubjectID(subjectID)) {
            this.subjectID = subjectID;
            this.subjectName = subjectName;
            this.credits = credits;
            this.instructor = instructor;
            this.preSubjectID = preSubjectID;
            this.maxStudent = maxStudent;
        } else {
            throw new IllegalArgumentException("Invalid Subject ID");
        }
        this.currentStudent = currentStudent;
    }

    private boolean isValidSubjectID(String id) {
        // subject id must start with "0550" or "9069" and be 8 characters long
        if (id.length() == 8 
        && (id.substring(0, 4).equals("0550") || (id.substring(0, 4).equals("9069")))) {
            return true;
        }
        return false;
    }

    private boolean isValidmaxStudent(String maxStudent) {
        int max = Integer.parseInt(maxStudent);
        if (max > 0 || max == -1) {
            return true;
        } 
        return false;
    }
}
