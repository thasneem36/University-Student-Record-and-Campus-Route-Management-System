/**
 * StudentLinkedList.java
 * A custom singly linked list that stores Student records.
 * Built from scratch with our own Node class (java.util.LinkedList is NOT used).
 *
 *   head -> [Student | next] -> [Student | next] -> ... -> null
 *
 * Author: Member 1 - Linked List & Student Records
 */
public class StudentLinkedList {

    /**
     * Node - one box in the chain.
     * Holds a Student and a reference (link) to the next node.
     */
    private static class Node {
        Student data;
        Node next;

        Node(Student data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;   // first node in the list (null when the list is empty)
    private int size;    // number of students currently stored

    public StudentLinkedList() {
        head = null;
        size = 0;
    }

    // ---------------------------------------------------------------
    // Validation helper
    // ---------------------------------------------------------------

    /**
     * Checks name, programme and marks.
     * Returns an error message, or null if everything is valid.
     */
    public static String validateDetails(String name, String programme, double marks) {
        if (name == null || name.trim().isEmpty()) {
            return "Name cannot be empty.";
        }
        if (programme == null || programme.trim().isEmpty()) {
            return "Programme cannot be empty.";
        }
        if (marks < 0 || marks > 100) {
            return "Marks must be between 0 and 100.";
        }
        return null;
    }

    // ---------------------------------------------------------------
    // Add
    // ---------------------------------------------------------------

    /**
     * Adds a student to the END of the list.
     * Steps:
     *   1. Reject empty / duplicate IDs and invalid details.
     *   2. Create a new node.
     *   3. If the list is empty, the new node becomes the head.
     *   4. Otherwise walk to the last node and link the new node after it.
     */
    public boolean addStudent(Student student) {
        if (student == null || student.getStudentId() == null
                || student.getStudentId().trim().isEmpty()) {
            System.out.println("Error: Student ID cannot be empty.");
            return false;
        }

        String error = validateDetails(student.getName(), student.getProgramme(), student.getMarks());
        if (error != null) {
            System.out.println("Error: " + error);
            return false;
        }

        if (searchStudent(student.getStudentId()) != null) {
            System.out.println("Error: A student with ID " + student.getStudentId() + " already exists.");
            return false;
        }

        Node newNode = new Node(student);

        if (head == null) {
            head = newNode;                 // list was empty
        } else {
            Node current = head;
            while (current.next != null) {  // walk to the last node
                current = current.next;
            }
            current.next = newNode;         // link new node at the end
        }

        size++;
        System.out.println("Student " + student.getStudentId() + " added successfully.");
        return true;
    }

    // ---------------------------------------------------------------
    // Search
    // ---------------------------------------------------------------

    /**
     * Linear search: walk node by node until the ID matches.
     * Returns the Student, or null if not found. Time: O(n).
     */
    public Student searchStudent(String studentId) {
        if (studentId == null) {
            return null;
        }
        Node current = head;
        while (current != null) {
            if (current.data.getStudentId().equalsIgnoreCase(studentId.trim())) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    // ---------------------------------------------------------------
    // Helpers
    // ---------------------------------------------------------------

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }

    // ---------------------------------------------------------------
    // Display
    // ---------------------------------------------------------------

    /**
     * Prints every student as a table by walking from head to the end.
     */
    public void displayAll() {
        if (isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        String line = "+------------+----------------------+----------------------+--------+";
        System.out.println(line);
        System.out.printf("| %-10s | %-20s | %-20s | %6s |%n", "ID", "Name", "Programme", "Marks");
        System.out.println(line);

        Node current = head;
        while (current != null) {
            Student s = current.data;
            System.out.printf("| %-10s | %-20s | %-20s | %6.2f |%n",
                    shorten(s.getStudentId(), 10),
                    shorten(s.getName(), 20),
                    shorten(s.getProgramme(), 20),
                    s.getMarks());
            current = current.next;
        }

        System.out.println(line);
        System.out.println("Total students: " + size);
    }

    // Cuts long text so the table columns stay aligned
    private String shorten(String text, int max) {
        if (text.length() <= max) {
            return text;
        }
        return text.substring(0, max - 3) + "...";
    }
}
