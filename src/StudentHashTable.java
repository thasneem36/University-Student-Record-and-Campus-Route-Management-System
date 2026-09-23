/**
 * StudentHashTable.java
 * A simple Hash Table for Student objects using an array of linked-list
 * buckets (separate chaining) to handle collisions.
 *
 * Table size is fixed at 17 (a prime number, which helps spread out
 * the hash values more evenly).
 *
 * Assumes a Student class already exists with:
 *   - String getStudentId()
 *   - String getName()
 *   - String getProgramme()
 *   - double getMarks()   (change to int if your Student uses int marks)
 *
 * CIT300 Data Structures - Student Record System
 */
public class StudentHashTable {

    // Fixed table size
    private static final int TABLE_SIZE = 17;

    // ---------- Inner Node class ----------
    // Each node is one link in a bucket's linked list.
    private class Node {
        Student data;
        Node next;

        Node(Student data) {
            this.data = data;
            this.next = null;
        }
    }

    // The array of buckets. Each slot is the head of a linked list.
    private Node[] table;

    // Constructor - creates an empty array of buckets
    public StudentHashTable() {
        table = new Node[TABLE_SIZE];
        // All slots start as null (empty buckets) by default in Java
    }

    // ================= HASH FUNCTION =================
    // Adds up the ASCII value of every character in the ID,
    // then uses mod to fit the result inside the table size.
    private int hash(String id) {
        int sum = 0;
        for (int i = 0; i < id.length(); i++) {
            sum += id.charAt(i);
        }
        return sum % TABLE_SIZE;
    }

    // ================= PUT (insert) =================
    public void put(Student student) {
        int index = hash(student.getStudentId());

        // Check the bucket first to avoid inserting a duplicate ID
        Node current = table[index];
        while (current != null) {
            if (current.data.getStudentId().equals(student.getStudentId())) {
                System.out.println("Insert failed: Student ID " + student.getStudentId()
                        + " already exists in the hash table.");
                return;
            }
            current = current.next;
        }

        // Create the new node and insert it at the FRONT of the bucket's list
        // (inserting at the front is O(1) - no need to walk to the end)
        Node newNode = new Node(student);
        newNode.next = table[index];
        table[index] = newNode;

        System.out.println("Student " + student.getStudentId() + " added to bucket " + index + ".");
    }

    // ================= GET (search) =================
    public Student get(String id) {
        int index = hash(id);
        Node current = table[index];

        // Walk along the linked list in this bucket looking for a match
        while (current != null) {
            if (current.data.getStudentId().equals(id)) {
                return current.data; // found it
            }
            current = current.next;
        }

        // If we reach here, we walked the whole bucket and found nothing
        System.out.println("Search: Student with ID " + id + " was not found.");
        return null;
    }

    // ================= REMOVE (delete) =================
    public void remove(String id) {
        int index = hash(id);
        Node current = table[index];
        Node previous = null;

        while (current != null) {
            if (current.data.getStudentId().equals(id)) {
                // Found the node to remove - unlink it from the list

                if (previous == null) {
                    // It's the first node in the bucket
                    table[index] = current.next;
                } else {
                    // Skip over the current node
                    previous.next = current.next;
                }

                System.out.println("Student with ID " + id + " has been removed from bucket " + index + ".");
                return;
            }
            previous = current;
            current = current.next;
        }

        // Walked the whole bucket without finding the student
        System.out.println("Delete failed: Student with ID " + id + " was not found.");
    }

    // ================= DISPLAY TABLE =================
    // Shows every bucket and the students chained inside it.
    public void displayTable() {
        System.out.println("\n----- Hash Table Contents (size " + TABLE_SIZE + ") -----");

        for (int i = 0; i < TABLE_SIZE; i++) {
            System.out.print("Bucket " + i + ": ");

            if (table[i] == null) {
                System.out.println("(empty)");
                continue;
            }

            // Walk the linked list in this bucket and print each student
            Node current = table[i];
            StringBuilder line = new StringBuilder();
            while (current != null) {
                line.append("[").append(current.data.getStudentId())
                    .append(" - ").append(current.data.getName()).append("]");
                if (current.next != null) {
                    line.append(" -> ");
                }
                current = current.next;
            }
            System.out.println(line.toString());
        }
        System.out.println("---------------------------------------------------\n");
    }
}
