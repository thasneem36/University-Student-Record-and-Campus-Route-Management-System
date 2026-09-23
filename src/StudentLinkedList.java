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
    // Update
    // ---------------------------------------------------------------

    /**
     * Finds the student by ID and replaces name, programme and marks.
     * The ID itself is not changed because it is the unique key.
     */
    public boolean updateStudent(String studentId, String name, String programme, double marks) {
        Student student = searchStudent(studentId);
        if (student == null) {
            System.out.println("Error: Student with ID " + studentId + " not found.");
            return false;
        }

        String error = validateDetails(name, programme, marks);
        if (error != null) {
            System.out.println("Error: " + error);
            return false;
        }

        student.setName(name.trim());
        student.setProgramme(programme.trim());
        student.setMarks(marks);
        System.out.println("Student " + student.getStudentId() + " updated successfully.");
        return true;
    }

    // ---------------------------------------------------------------
    // Delete
    // ---------------------------------------------------------------

    /**
     * Removes the student with the given ID and returns it
     * (so it can be pushed onto the undo/history stack).
     *
     * Case 1: node to delete is the head -> move head to head.next
     * Case 2: node is in the middle/end  -> keep a "previous" pointer and
     *         link previous.next to current.next (skipping the node)
     *
     * Returns null if the ID was not found.
     */
    public Student deleteStudent(String studentId) {
        if (isEmpty()) {
            System.out.println("Error: The list is empty. Nothing to delete.");
            return null;
        }
        if (studentId == null) {
            System.out.println("Error: Student ID cannot be empty.");
            return null;
        }
        String id = studentId.trim();

        // Case 1: delete the head node
        if (head.data.getStudentId().equalsIgnoreCase(id)) {
            Student removed = head.data;
            head = head.next;
            size--;
            System.out.println("Student " + removed.getStudentId() + " deleted successfully.");
            return removed;
        }

        // Case 2: search the rest of the list, remembering the previous node
        Node previous = head;
        Node current = head.next;
        while (current != null) {
            if (current.data.getStudentId().equalsIgnoreCase(id)) {
                previous.next = current.next;   // unlink the node
                size--;
                System.out.println("Student " + current.data.getStudentId() + " deleted successfully.");
                return current.data;
            }
            previous = current;
            current = current.next;
        }

        System.out.println("Error: Student with ID " + id + " not found.");
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

    /**
     * Copies all students into an array (in list order).
     * Useful for other members, e.g. rebuilding the BST or hash table.
     */
    public Student[] toArray() {
        Student[] students = new Student[size];
        Node current = head;
        int index = 0;
        while (current != null) {
            students[index++] = current.data;
            current = current.next;
        }
        return students;
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
