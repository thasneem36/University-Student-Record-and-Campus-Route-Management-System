/**
 * StudentBST.java
 * A simple Binary Search Tree (BST) that stores Student objects,
 * ordered by studentId (using String's compareTo method).
 *
 * Assumes a Student class already exists with:
 *   - String getStudentId()
 *   - String getName()
 *   - String getProgramme()
 *   - double getMarks()   (change to int if your Student uses int marks)
 *
 * CIT300 Data Structures - Student Record System
 */
public class StudentBST {

    // ---------- Inner Node class ----------
    // Each node holds one Student and links to left/right children.
    private class Node {
        Student data;
        Node left;
        Node right;

        Node(Student data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    // The tree only needs to remember its root node.
    private Node root;

    // Constructor - starts as an empty tree
    public StudentBST() {
        root = null;
    }

    // ---------- isEmpty ----------
    public boolean isEmpty() {
        return root == null;
    }

    // ================= INSERT =================
    // Public method - starts the recursion from the root
    public void insert(Student student) {
        root = insertHelper(root, student);
    }

    // Private recursive helper that actually walks the tree
    private Node insertHelper(Node current, Student student) {
        // Base case: found an empty spot, place the new node here
        if (current == null) {
            return new Node(student);
        }

        int comparison = student.getStudentId().compareTo(current.data.getStudentId());

        if (comparison < 0) {
            // New student's ID is smaller -> go left
            current.left = insertHelper(current.left, student);
        } else if (comparison > 0) {
            // New student's ID is bigger -> go right
            current.right = insertHelper(current.right, student);
        } else {
            // comparison == 0 means the ID already exists - reject duplicate
            System.out.println("Insert failed: Student ID " + student.getStudentId()
                    + " already exists in the BST.");
        }

        return current;
    }

    // ================= SEARCH =================
    public Student search(String id) {
        Node result = searchHelper(root, id);
        if (result == null) {
            System.out.println("Search: Student with ID " + id + " was not found.");
            return null;
        }
        return result.data;
    }

    private Node searchHelper(Node current, String id) {
        // Base case: reached the end of a branch without finding it
        if (current == null) {
            return null;
        }

        int comparison = id.compareTo(current.data.getStudentId());

        if (comparison == 0) {
            return current; // found it
        } else if (comparison < 0) {
            return searchHelper(current.left, id);  // look in left subtree
        } else {
            return searchHelper(current.right, id); // look in right subtree
        }
    }

    // ================= DELETE =================
    // Public method - starts the recursion from the root
    public void delete(String id) {
        // First check if the student actually exists, so we can print a clear message
        if (searchHelper(root, id) == null) {
            System.out.println("Delete failed: Student with ID " + id + " was not found.");
            return;
        }
        root = deleteHelper(root, id);
        System.out.println("Student with ID " + id + " has been deleted.");
    }

    private Node deleteHelper(Node current, String id) {
        if (current == null) {
            return null; // nothing to delete here
        }

        int comparison = id.compareTo(current.data.getStudentId());

        if (comparison < 0) {
            // Target is smaller -> keep searching in left subtree
            current.left = deleteHelper(current.left, id);
        } else if (comparison > 0) {
            // Target is bigger -> keep searching in right subtree
            current.right = deleteHelper(current.right, id);
        } else {
            // ---- We found the node to delete ----

            // Case 1: No children (leaf node)
            if (current.left == null && current.right == null) {
                return null;
            }

            // Case 2: One child - return the existing child to "skip" this node
            if (current.left == null) {
                return current.right;
            }
            if (current.right == null) {
                return current.left;
            }

            // Case 3: Two children
            // Find the smallest student in the right subtree (the "in-order successor").
            // This keeps the BST correctly ordered after removal.
            Student successor = findMin(current.right);

            // Copy the successor's data into this node
            current.data = successor;

            // Delete the successor from the right subtree (it now has a duplicate to remove)
            current.right = deleteHelper(current.right, successor.getStudentId());
        }

        return current;
    }

    // Helper to find the student with the smallest ID in a subtree
    // (used to find the in-order successor during deletion)
    private Student findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node.data;
    }

    // ================= IN-ORDER DISPLAY =================
    // Prints all students sorted by studentId, in a neat table.
    public void inOrderDisplay() {
        if (isEmpty()) {
            System.out.println("The BST is empty. No students to display.");
            return;
        }

        System.out.println("\n----- Student Records (sorted by ID) -----");
        System.out.printf("%-12s %-20s %-20s %-8s%n", "ID", "Name", "Programme", "Marks");
        System.out.println("---------------------------------------------------------------");
        inOrderHelper(root);
        System.out.println("---------------------------------------------------------------\n");
    }

    // Recursive in-order traversal: left -> current node -> right
    // Visiting in this order naturally prints students in ascending ID order.
    private void inOrderHelper(Node current) {
        if (current == null) {
            return;
        }
        inOrderHelper(current.left);
        System.out.printf("%-12s %-20s %-20s %-8.2f%n",
                current.data.getStudentId(),
                current.data.getName(),
                current.data.getProgramme(),
                current.data.getMarks());
        inOrderHelper(current.right);
    }
}
