package Model;

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
    }

    private boolean isValidID(String id) {
        // student id must start with "69" and be 8 characters long
        if ((id.substring(0, 2).equals("69")) && id.length() == 8) {
            return true;
        }
        return false;
    }

    public String getId() { return id; }
    public String getPrefix() { return prefix; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getSchool() { return school; }
    public String getEmail() { return email; }
}