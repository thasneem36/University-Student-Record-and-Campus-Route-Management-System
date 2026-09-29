import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

/**
 * CIT300 Data Structures - University Campus Route System
 *
 * The campus is an UNDIRECTED GRAPH stored as an ADJACENCY LIST:
 *   - each location is a vertex (a key in the map)
 *   - each road is an edge (each location's list holds its neighbours)
 *
 * Names are compared ignoring case ("library" = "Library").
 */
public class CampusGraph {

    // Key = location name, Value = list of neighbouring locations.
    // LinkedHashMap keeps the order in which locations were added.
    private Map<String, List<String>> adjList = new LinkedHashMap<>();

    // ------------------------------------------------------------------
    // Helper methods
    // ------------------------------------------------------------------

    // Finds the stored name that matches 'name' ignoring case.
    // Returns null if the location does not exist.
    private String findLocation(String name) {
        if (name == null) {
            return null;
        }
        for (String location : adjList.keySet()) {
            if (location.equalsIgnoreCase(name.trim())) {
                return location;
            }
        }
        return null;
    }

    // Checks whether 'name' is already in a list, ignoring case.
    private boolean listContains(List<String> list, String name) {
        for (String item : list) {
            if (item.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    // Removes 'name' from a list, ignoring case.
    private void removeFromList(List<String> list, String name) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equalsIgnoreCase(name)) {
                list.remove(i);
                return;
            }
        }
    }

    // ------------------------------------------------------------------
    // Graph operations
    // ------------------------------------------------------------------

    // Adds a new vertex. Rejects empty names and duplicates.
    public void addLocation(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Error: location name cannot be empty.");
            return;
        }
        name = name.trim();
        if (findLocation(name) != null) {
            System.out.println("Error: '" + name + "' already exists.");
            return;
        }
        adjList.put(name, new ArrayList<>());
        System.out.println("Added location: " + name);
    }

    // Removes a vertex AND every road that touches it.
    public void removeLocation(String name) {
        String location = findLocation(name);
        if (location == null) {
            System.out.println("Error: '" + name + "' does not exist.");
            return;
        }
        // Step 1: remove this location from every neighbour's list
        for (String neighbour : adjList.get(location)) {
            removeFromList(adjList.get(neighbour), location);
        }
        // Step 2: remove the location itself
        adjList.remove(location);
        System.out.println("Removed location: " + location + " (and all its roads)");
    }

    // Adds an undirected edge (a -> b and b -> a).
    public void addConnection(String a, String b) {
        String locA = findLocation(a);
        String locB = findLocation(b);

        if (locA == null || locB == null) {
            System.out.println("Error: both locations must exist.");
            return;
        }
        if (locA.equals(locB)) {
            System.out.println("Error: a location cannot connect to itself.");
            return;
        }
        if (listContains(adjList.get(locA), locB)) {
            System.out.println("Error: road " + locA + " - " + locB + " already exists.");
            return;
        }
        adjList.get(locA).add(locB); // direction 1
        adjList.get(locB).add(locA); // direction 2
        System.out.println("Added road: " + locA + " - " + locB);
    }

    // Removes the edge in both directions.
    public void removeConnection(String a, String b) {
        String locA = findLocation(a);
        String locB = findLocation(b);

        if (locA == null || locB == null) {
            System.out.println("Error: both locations must exist.");
            return;
        }
        if (!listContains(adjList.get(locA), locB)) {
            System.out.println("Error: no road between " + locA + " and " + locB + ".");
            return;
        }
        removeFromList(adjList.get(locA), locB);
        removeFromList(adjList.get(locB), locA);
        System.out.println("Removed road: " + locA + " - " + locB);
    }

    // Prints every location followed by its neighbours.
    public void displayConnections() {
        if (adjList.isEmpty()) {
            System.out.println("The campus map is empty.");
            return;
        }
        System.out.println("\n--- Campus Map ---");
        for (String location : adjList.keySet()) {
            System.out.println(location + " -> " + adjList.get(location));
        }
    }

    // ------------------------------------------------------------------
    // Traversals
    // ------------------------------------------------------------------

    // Breadth-First Search: visits closest locations first, uses a QUEUE.
    public void bfs(String start) {
        String startLoc = findLocation(start);
        if (startLoc == null) {
            System.out.println("Error: '" + start + "' does not exist.");
            return;
        }

        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        visited.add(startLoc);
        queue.add(startLoc);

        System.out.print("BFS from " + startLoc + ": ");
        while (!queue.isEmpty()) {
            String current = queue.remove();      // take from the front
            System.out.print(current + "  ");

            for (String neighbour : adjList.get(current)) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);       // mark when added to the queue
                    queue.add(neighbour);         // add to the back
                }
            }
        }
        System.out.println();
    }

    // Depth-First Search: goes as deep as possible first (recursion).
    public void dfs(String start) {
        String startLoc = findLocation(start);
        if (startLoc == null) {
            System.out.println("Error: '" + start + "' does not exist.");
            return;
        }
        Set<String> visited = new HashSet<>();
        System.out.print("DFS from " + startLoc + ": ");
        dfsHelper(startLoc, visited);
        System.out.println();
    }

    // Recursive helper for dfs()
    private void dfsHelper(String current, Set<String> visited) {
        visited.add(current);
        System.out.print(current + "  ");

        for (String neighbour : adjList.get(current)) {
            if (!visited.contains(neighbour)) {
                dfsHelper(neighbour, visited);
            }
        }
    }

    // ------------------------------------------------------------------
    // Sample data
    // ------------------------------------------------------------------

    public void loadSampleData() {
        addLocation("Main Gate");
        addLocation("Library");
        addLocation("Canteen");
        addLocation("Lab Block");
        addLocation("Admin");
        addLocation("Auditorium");

        addConnection("Main Gate", "Library");
        addConnection("Main Gate", "Admin");
        addConnection("Library", "Canteen");
        addConnection("Library", "Lab Block");
        addConnection("Admin", "Lab Block");
        addConnection("Canteen", "Auditorium");
        addConnection("Lab Block", "Auditorium");
    }

    // ------------------------------------------------------------------
    // Console menu
    // ------------------------------------------------------------------

    public static void main(String[] args) {
        CampusGraph campus = new CampusGraph();
        Scanner input = new Scanner(System.in);

        campus.loadSampleData();
        System.out.println("\nSample data loaded.");

        int choice = -1;
        while (choice != 0) {
            System.out.println("\n===== Campus Route System =====");
            System.out.println("1. Add location");
            System.out.println("2. Remove location");
            System.out.println("3. Add road");
            System.out.println("4. Remove road");
            System.out.println("5. Display connections");
            System.out.println("6. BFS traversal");
            System.out.println("7. DFS traversal");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            try {
                choice = Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number from the menu.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Location name: ");
                    campus.addLocation(input.nextLine());
                    break;
                case 2:
                    System.out.print("Location to remove: ");
                    campus.removeLocation(input.nextLine());
                    break;
                case 3:
                    System.out.print("First location: ");
                    String a1 = input.nextLine();
                    System.out.print("Second location: ");
                    String b1 = input.nextLine();
                    campus.addConnection(a1, b1);
                    break;
                case 4:
                    System.out.print("First location: ");
                    String a2 = input.nextLine();
                    System.out.print("Second location: ");
                    String b2 = input.nextLine();
                    campus.removeConnection(a2, b2);
                    break;
                case 5:
                    campus.displayConnections();
                    break;
                case 6:
                    System.out.print("Start location: ");
                    campus.bfs(input.nextLine());
                    break;
                case 7:
                    System.out.print("Start location: ");
                    campus.dfs(input.nextLine());
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
        input.close();
    }
}
