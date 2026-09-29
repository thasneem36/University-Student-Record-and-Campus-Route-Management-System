/**
 * ServiceQueue.java
 * A custom linked queue built from scratch using front/rear pointers.
 * Does NOT use java.util.Queue.
 *
 * A queue is FIFO: First In, First Out. Whichever request was
 * enqueued first is the first one dequeued (processed) - i.e.
 * first come, first served.
 */
public class ServiceQueue {

    // Node class for the linked queue
    private class Node {
        ServiceRequest request;
        Node next;

        Node(ServiceRequest request) {
            this.request = request;
            this.next = null;
        }
    }

    private Node front;  // where we remove from
    private Node rear;   // where we add to
    private int size;

    public ServiceQueue() {
        front = null;
        rear = null;
        size = 0;
    }

    /**
     * Add a new service request to the back of the queue.
     */
    public void enqueue(ServiceRequest request) {
        if (request == null) {
            System.out.println("Cannot enqueue a null request.");
            return;
        }

        Node newNode = new Node(request);

        if (isEmpty()) {
            // Queue was empty, so this node is both front and rear
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    /**
     * Remove and return the request at the front of the queue
     * (the one that has been waiting the longest).
     * Returns null and prints a message if the queue is empty.
     */
    public ServiceRequest dequeue() {
        if (isEmpty()) {
            System.out.println("Service queue is empty - no requests to process.");
            return null;
        }

        ServiceRequest removed = front.request;
        front = front.next;

        // If we removed the last node, reset rear too
        if (front == null) {
            rear = null;
        }

        size--;
        return removed;
    }

    /**
     * Look at the next request to be processed without removing it.
     */
    public ServiceRequest peek() {
        if (isEmpty()) {
            System.out.println("Service queue is empty - nothing to peek.");
            return null;
        }
        return front.request;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        return size;
    }

    /**
     * Print every request currently waiting in the queue, in order
     * (front/next-to-be-served first).
     */
    public void displayAll() {
        if (isEmpty()) {
            System.out.println("No pending service requests.");
            return;
        }

        System.out.println("----- Pending Service Requests -----");
        Node current = front;
        int position = 1;
        while (current != null) {
            System.out.println(position + ". " + current.request);
            current = current.next;
            position++;
        }
        System.out.println("------------------------------------");
    }
}
