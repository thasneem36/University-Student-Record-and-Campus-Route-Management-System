import java.util.Scanner;

/**
 * Main.java
 * Menu-driven console interface for the
 * University Student Record and Campus Route Management System.
 *
 * Menu options 1-4 : Member 1 (Linked List & Student Records)
 * Menu options 5-7 : Member 2 (Stack & Queue)
 * Menu options 8-9 : Member 3 (BST/AVL & Hashing)
 * Menu options 10-15: Member 4 (Graph)
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentLinkedList studentList = new StudentLinkedList();

    public static void main(String[] args) {
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
                case 6:
                case 7:
                // ---------- Member 3: BST & Hashing ----------
                case 8:
                case 9:
                // ---------- Member 4: Graph ----------
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                    System.out.println("This option is not implemented yet.");
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
        System.out.println(" 8. Display Students using BST/AVL");
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
        String id = readNonEmpty("Enter Student ID: ");
        if (studentList.searchStudent(id) != null) {
            System.out.println("Error: A student with ID " + id + " already exists.");
            return;
        }
        String name = readNonEmpty("Enter Name: ");
        String programme = readNonEmpty("Enter Programme: ");
        double marks = readMarks("Enter Marks (0-100): ");

        studentList.addStudent(new Student(id, name, programme, marks));
    }

    // Option 2
    private static void updateStudentRecord() {
        System.out.println("\n--- Update Student Record ---");
        if (studentList.isEmpty()) {
            System.out.println("No student records found. Add a student first.");
            return;
        }
        String id = readNonEmpty("Enter Student ID to update: ");
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

        studentList.updateStudent(id, name, programme, marks);
    }

    // Option 3
    private static void deleteStudentRecord() {
        System.out.println("\n--- Delete Student Record ---");
        if (studentList.isEmpty()) {
            System.out.println("No student records found. Nothing to delete.");
            return;
        }
        String id = readNonEmpty("Enter Student ID to delete: ");
        studentList.deleteStudent(id);
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
