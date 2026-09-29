import java.util.Scanner;

/**
 * Main.java
 * Menu-driven console interface for the
 * University Student Record and Campus Route Management System.
 *
 * Menu options 1-4  : Member 1 (Linked List & Student Records)
 * Menu options 5-7  : Member 2 (Stack & Queue)
 * Menu options 8-9  : Member 3 (BST & Hashing)
 * Menu options 10-15: Member 4 (Graph)
 *
 * The linked list is the main store. Every add / delete is also applied
 * to the BST and the hash table so all three always hold the same students.
 * Updates don't need syncing: all three structures share the same Student objects.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final StudentLinkedList studentList = new StudentLinkedList();
    private static final ActionStack actionStack = new ActionStack();
    private static final ServiceQueue serviceQueue = new ServiceQueue();
    private static final StudentBST studentBST = new StudentBST();
    private static final StudentHashTable studentHashTable = new StudentHashTable();
    private static final CampusGraph campusGraph = new CampusGraph();

    public static void main(String[] args) {
        System.out.println("Loading sample campus map...");
        campusGraph.loadSampleData();

        boolean running = true;

        while (running) {
            printMenu();
            int choice = readMenuChoice();

            switch (choice) {
                // ---------- Member 1: Linked List ----------
                case 1:
                    addStudentRecord();
                    break;
                case 2:
                    updateStudentRecord();
                    break;
                case 3:
                    deleteStudentRecord();
                    break;
                case 4:
                    studentList.displayAll();
                    break;

                // ---------- Member 2: Stack & Queue ----------
                case 5:
                    addServiceRequest();
                    break;
                case 6:
                    processServiceRequest();
                    break;
                case 7:
                    displayRecentActions();
                    break;

                // ---------- Member 3: BST & Hashing ----------
                case 8:
                    studentBST.inOrderDisplay();
                    break;
                case 9:
                    searchStudentByHash();
                    break;

                // ---------- Member 4: Graph ----------
                case 10:
                    campusGraph.addLocation(readNonEmpty("Enter location name: "));
                    break;
                case 11:
                    campusGraph.removeLocation(readNonEmpty("Enter location to remove: "));
                    break;
                case 12:
                    campusGraph.addConnection(readNonEmpty("Enter first location: "),
                            readNonEmpty("Enter second location: "));
                    break;
                case 13:
                    campusGraph.removeConnection(readNonEmpty("Enter first location: "),
                            readNonEmpty("Enter second location: "));
                    break;
                case 14:
                    campusGraph.displayConnections();
                    break;
                case 15:
                    traverseCampus();
                    break;

                case 16:
                    running = false;
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 16.");
            }
        }
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("==========================================================");
        System.out.println(" University Student Record & Campus Route Management System");
        System.out.println("==========================================================");
        System.out.println(" 1. Add Student Record");
        System.out.println(" 2. Update Student Record");
        System.out.println(" 3. Delete Student Record");
        System.out.println(" 4. Display All Records using Linked List");
        System.out.println(" 5. Add Service Request to Queue");
        System.out.println(" 6. Process Next Service Request");
        System.out.println(" 7. Display Recent Actions using Stack");
        System.out.println(" 8. Display Students using BST");
        System.out.println(" 9. Search Student using Hashing");
        System.out.println("10. Add Campus Location");
        System.out.println("11. Remove Campus Location");
        System.out.println("12. Add Campus Connection/Road");
        System.out.println("13. Remove Campus Connection/Road");
        System.out.println("14. Display Campus Connections");
        System.out.println("15. Traverse Campus Locations using BFS or DFS");
        System.out.println("16. Exit");
        System.out.print("Enter your choice: ");
    }

    // =================================================================
    // Member 1: Student record operations (menu options 1-4)
    // =================================================================

    // Option 1
    private static void addStudentRecord() {
        System.out.println("\n--- Add Student Record ---");
        String id = readStudentId("Enter Student ID: ");
        if (studentList.searchStudent(id) != null) {
            System.out.println("Error: A student with ID " + id + " already exists.");
            return;
        }
        String name = readNonEmpty("Enter Name: ");
        String programme = readNonEmpty("Enter Programme: ");
        double marks = readMarks("Enter Marks (0-100): ");

        Student student = new Student(id, name, programme, marks);
        if (studentList.addStudent(student)) {
            studentBST.insert(student);
            studentHashTable.put(student);
            actionStack.push("Added student " + id);
        }
    }

    // Option 2
    private static void updateStudentRecord() {
        System.out.println("\n--- Update Student Record ---");
        if (studentList.isEmpty()) {
            System.out.println("No student records found. Add a student first.");
            return;
        }
        String id = readStudentId("Enter Student ID to update: ");
        Student existing = studentList.searchStudent(id);
        if (existing == null) {
            System.out.println("Error: Student with ID " + id + " not found.");
            return;
        }

        System.out.println("Current record: " + existing);
        System.out.println("(Press Enter to keep the current value)");

        String name = readLine("New Name [" + existing.getName() + "]: ");
        if (name.isEmpty()) {
            name = existing.getName();
        }
        String programme = readLine("New Programme [" + existing.getProgramme() + "]: ");
        if (programme.isEmpty()) {
            programme = existing.getProgramme();
        }
        double marks = readOptionalMarks("New Marks [" + existing.getMarks() + "]: ", existing.getMarks());

        if (studentList.updateStudent(id, name, programme, marks)) {
            actionStack.push("Updated student " + id);
        }
    }

    // Option 3
    private static void deleteStudentRecord() {
        System.out.println("\n--- Delete Student Record ---");
        if (studentList.isEmpty()) {
            System.out.println("No student records found. Nothing to delete.");
            return;
        }
        String id = readStudentId("Enter Student ID to delete: ");
        Student removed = studentList.deleteStudent(id);
        if (removed != null) {
            studentBST.delete(removed.getStudentId());
            studentHashTable.remove(removed.getStudentId());
            actionStack.push("Deleted student " + removed.getStudentId());
            actionStack.pushDeleted(removed);   // saved so the delete can be undone
        }
    }

    // =================================================================
    // Member 2: Queue & Stack operations (menu options 5-7)
    // =================================================================

    // Option 5
    private static void addServiceRequest() {
        System.out.println("\n--- Add Service Request ---");
        String id = readStudentId("Enter Student ID: ");
        if (studentList.searchStudent(id) == null) {
            System.out.println("Error: Student with ID " + id + " not found. Add the student first.");
            return;
        }
        String description = readNonEmpty("Enter request description: ");

        ServiceRequest request = new ServiceRequest(id, description);
        serviceQueue.enqueue(request);
        actionStack.push("Added service request #" + request.getRequestId() + " for " + id);
        System.out.println("Request #" + request.getRequestId() + " added to the queue. "
                + "Requests waiting: " + serviceQueue.size());
    }

    // Option 6
    private static void processServiceRequest() {
        System.out.println("\n--- Process Next Service Request ---");
        ServiceRequest request = serviceQueue.dequeue();
        if (request != null) {
            System.out.println("Processed: " + request);
            actionStack.push("Processed service request #" + request.getRequestId());
            System.out.println("Requests still waiting: " + serviceQueue.size());
            serviceQueue.displayAll();
        }
    }

    // Option 7
    private static void displayRecentActions() {
        System.out.println();
        actionStack.displayRecent();

        if (actionStack.isDeletedStackEmpty()) {
            return;
        }
        String answer = readLine("Undo the last delete? (y/n): ");
        if (answer.equalsIgnoreCase("y")) {
            undoLastDelete();
        }
    }

    // Restores the most recently deleted student into all structures
    private static void undoLastDelete() {
        Student restored = actionStack.popDeleted();
        if (restored == null) {
            return;
        }
        if (studentList.addStudent(restored)) {
            studentBST.insert(restored);
            studentHashTable.put(restored);
            actionStack.push("Undo: restored student " + restored.getStudentId());
        }
    }

    // =================================================================
    // Member 3: Hashing (menu option 9)
    // =================================================================

    // Option 9
    private static void searchStudentByHash() {
        System.out.println("\n--- Search Student using Hashing ---");
        String id = readStudentId("Enter Student ID to search: ");
        Student found = studentHashTable.get(id);
        if (found != null) {
            System.out.println("Found: " + found);
        }
    }

    // =================================================================
    // Member 4: Graph traversal (menu option 15)
    // =================================================================

    // Option 15
    private static void traverseCampus() {
        System.out.println("\n--- Traverse Campus Locations ---");
        String type = readNonEmpty("Choose traversal (1 = BFS, 2 = DFS): ");
        if (!type.equals("1") && !type.equals("2")) {
            System.out.println("Error: Please enter 1 for BFS or 2 for DFS.");
            return;
        }
        String start = readNonEmpty("Enter start location: ");
        if (type.equals("1")) {
            campusGraph.bfs(start);
        } else {
            campusGraph.dfs(start);
        }
    }

    // =================================================================
    // Input helpers (with validation)
    // =================================================================

    // Reads one line; exits cleanly if input ends (e.g. Ctrl+Z / Ctrl+D)
    private static String readLine(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            System.out.println("\nInput closed. Exiting.");
            System.exit(0);
        }
        return scanner.nextLine().trim();
    }

    // Keeps asking until the user types something
    private static String readNonEmpty(String prompt) {
        while (true) {
            String value = readLine(prompt);
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Error: This field cannot be empty. Please try again.");
        }
    }

    // Student IDs are stored in upper case so "s001" and "S001" are the same
    // student in the linked list, BST and hash table.
    private static String readStudentId(String prompt) {
        return readNonEmpty(prompt).toUpperCase();
    }

    // Keeps asking until a number between 0 and 100 is entered
    private static double readMarks(String prompt) {
        while (true) {
            String value = readLine(prompt);
            try {
                double marks = Double.parseDouble(value);
                if (marks >= 0 && marks <= 100) {
                    return marks;
                }
                System.out.println("Error: Marks must be between 0 and 100.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number for marks.");
            }
        }
    }

    // Same as readMarks, but Enter keeps the current value
    private static double readOptionalMarks(String prompt, double currentMarks) {
        while (true) {
            String value = readLine(prompt);
            if (value.isEmpty()) {
                return currentMarks;
            }
            try {
                double marks = Double.parseDouble(value);
                if (marks >= 0 && marks <= 100) {
                    return marks;
                }
                System.out.println("Error: Marks must be between 0 and 100.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number for marks.");
            }
        }
    }

    // Reads the menu choice; returns -1 for non-numeric input
    private static int readMenuChoice() {
        if (!scanner.hasNextLine()) {
            System.out.println("\nInput closed. Exiting.");
            System.exit(0);
        }
        String value = scanner.nextLine().trim();
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
