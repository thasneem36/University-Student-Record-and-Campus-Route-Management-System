/**
* ActionStack.java
* A custom stack built from scratch using
a simple Node class.
* Does NOT use java.util.Stack.
*
* Used for two things:
*   1) Recording action messages like
"Added S001", "Deleted S002"
*   2) Keeping a separate stack of deleted
Student objects, so we can
*      support an "undo last delete"
feature (bonus).
*
* A stack is LIFO: Last In, First Out.
Whatever was pushed most
* recently is the first thing that comes
off with pop().
*/
public class ActionStack {
    // ---- Node class for the action￾message stack ----
    private class ActionNode {
        String action;
        ActionNode next;
        ActionNode(String action) {
            this.action = action;
            this.next = null;
        }
    }
    // ---- Node class for the deleted￾student stack (undo feature) ----
    private class StudentNode {
        Student student;
        StudentNode next;
        StudentNode(Student student) {
            this.student = student;
            this.next = null;
        }
    }
    private ActionNode top;          // top
of the action-message stack
    private StudentNode deletedTop;  // top
of the deleted-students stack
    private int size;                // how
many actions are currently stored
    public ActionStack() {
        top = null;
        deletedTop = null;
        size = 0;
    }
    // ================= Action message
stack =================
    /**
     * Push a new action message onto the
stack.
     * Example: push("Added S001")
     */
    public void push(String action) {
        ActionNode newNode = new
ActionNode(action);
        newNode.next = top;
        top = newNode;
        size++;
    }
    /**
     * Remove and return the most recent
action message.
     * Returns null and prints a message if
the stack is empty.
     */
    public String pop() {
        if (isEmpty()) {
            System.out.println("Action
stack is empty - nothing to pop.");
            return null;
        }
        String removedAction = top.action;
        top = top.next;
        size--;
        return removedAction;
    }
    /**
     * Look at the most recent action
message without removing it.
     */
    public String peek() {
        if (isEmpty()) {
            System.out.println("Action
stack is empty - nothing to peek.");
            return null;
        }
        return top.action;
    }
    public boolean isEmpty() {
        return top == null;
    }
    public int size() {
        return size;
    }
    /**
     * Print the most recent actions,
newest first.
     * If limit <= 0, prints ALL stored
actions.
     */
    public void displayRecent(int limit) {
        if (isEmpty()) {
            System.out.println("No actions
recorded yet.");
            return;
        }
        System.out.println("----- Recent
Actions (newest first) -----");
        ActionNode current = top;
        int count = 0;
        while (current != null && (limit <=
0 || count < limit)) {
            System.out.println((count + 1)
+ ". " + current.action);
            current = current.next;
            count++;
        }
        System.out.println("---------------
---------------------------");
    }
    // Overload so displayRecent() with no
args shows everything
    public void displayRecent() {
        displayRecent(0);
    }
    // ================= Deleted-student
stack (undo bonus) =================
    /**
     * Push a deleted Student onto the undo
stack.
     * Call this right before/after
removing a student from the system.
     */
    public void pushDeleted(Student
student) {
        if (student == null) {
            System.out.println("Cannot push
a null student to the undo stack.");
            return;
        }
        StudentNode newNode = new
StudentNode(student);
        newNode.next = deletedTop;
        deletedTop = newNode;
    }
    /**
     * Pop the most recently deleted
student off the undo stack.
     * Returns null and prints a message if
there is nothing to undo.
     */
    public Student popDeleted() {
        if (deletedTop == null) {
            System.out.println("No deleted
students to undo.");
            return null;
        }
        Student restored =
deletedTop.student;
        deletedTop = deletedTop.next;
        return restored;
    }
    public boolean isDeletedStackEmpty() {
        return deletedTop == null;
    }
}