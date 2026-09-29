/**
 * ServiceRequest.java
 * Simple data class representing one student service request.
 *
 * Fields:
 *   requestId   - auto-increment ID, assigned automatically
 *   studentId   - which student made the request
 *   description - what the request is about
 */
public class ServiceRequest {

    // Static counter shared by all ServiceRequest objects,
    // so each new request automatically gets the next ID.
    private static int nextId = 1;

    private int requestId;
    private String studentId;
    private String description;

    public ServiceRequest(String studentId, String description) {
        this.requestId = nextId;
        nextId++;
        this.studentId = studentId;
        this.description = description;
    }

    public int getRequestId() {
        return requestId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Request #" + requestId + " | Student: " + studentId
                + " | Description: " + description;
    }
}
