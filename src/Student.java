/**
 * Student.java
 * Represents one student record in the system.
 * Shared by all members (linked list, stack, BST, hashing).
 *
 * Author: Member 1 - Linked List & Student Records
 */
public class Student {

    private String studentId;
    private String name;
    private String programme;
    private double marks;

    // Constructor - creates a new student record
    public Student(String studentId, String name, String programme, double marks) {
        this.studentId = studentId;
        this.name = name;
        this.programme = programme;
        this.marks = marks;
    }

    // ---------- Getters ----------
    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getProgramme() {
        return programme;
    }

    public double getMarks() {
        return marks;
    }

    // ---------- Setters ----------
    // Student ID has no setter: it is the unique key and must not change.
    public void setName(String name) {
        this.name = name;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "ID: " + studentId
                + " | Name: " + name
                + " | Programme: " + programme
                + " | Marks: " + marks;
    }
}
