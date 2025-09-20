
import java.time.LocalDate;

public class Student {
    private String id;
    private String prefix;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String school;
    private String email;

    public Student(String id, String prefix, String firstName, String lastName, LocalDate dateOfBirth, String school, String email) {
        try {
            if (isValidID(id)) {
                this.id = id;
                this.prefix = prefix;
                this.firstName = firstName;
                this.lastName = lastName;
                this.dateOfBirth = dateOfBirth;
                this.school = school;
                this.email = email;
            } else {
                throw new IllegalArgumentException("Invalid ID: ID must start with '69' and be 8 characters long");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean isValidID(String id) {
        if ((id.charAt(0) == '6' && id.charAt(1) == '9') && id.length() == 8) {
            return true;
        }
        return false;
    }
}