# University Student Record and Campus Route Management System

CIT300 Data Structures and Algorithms - Graded Practical Assignment 1 (Week 10)

A Java console application that manages university student records and represents
connections between campus locations, using linked lists, stacks, queues, trees,
hashing and graphs.

## How to Run

```bash
cd src
javac -d ../out *.java
java -cp ../out Main
```

## Group Members

| Member | Name | Student ID | Responsibility |
|---|---|---|---|
| Member 1 | HMM. Thasneem | 23DA2-0473 | Linked list implementation and student-record management |
| Member 2 | H.f.sawra | 23da2-0605 | Stack and queue implementation and related operations |
| Member 3 | M.N.Nishath BEGAM | 23da2-0764 | BST/AVL tree implementation and hashing/search functionality |
| Member 4 | A.Maryam Amani | 23da2-0857 | Graph implementation, campus locations, connections and BFS/DFS traversal |

## Individual Contributions

### Member 1 - Linked List & Student Records

**Files:** `src/Student.java`, `src/StudentLinkedList.java`, `src/Main.java` (menu options 1-4)

- **`Student.java`** - the student record class (Student ID, Name, Programme, Marks) with
  constructor, getters, setters and `toString()`. Shared by the other members' data structures.
- **`StudentLinkedList.java`** - a custom singly linked list built with its own `Node` class
  (`java.util.LinkedList` is not used).
  - `addStudent(Student)` - adds a node at the end of the list; rejects duplicate IDs
  - `updateStudent(id, name, programme, marks)` - updates an existing record
  - `deleteStudent(id)` - unlinks the node (head case and middle/end case) and returns the
    deleted `Student` so it can be pushed onto the undo/history stack
  - `searchStudent(id)` - linear search through the nodes
  - `displayAll()` - prints all records as a table
  - `isEmpty()`, `size()`, `toArray()` - helpers (`toArray()` lets other structures be built from the list)
- **Validation:** duplicate Student ID, empty ID/name/programme, marks must be a number
  between 0 and 100, student not found, and invalid menu choices.
- **Menu options:** 1. Add, 2. Update (press Enter to keep a current value), 3. Delete,
  4. Display All.

### Member 2 - Stack & Queue

**Files:** `src/ActionStack.java`, `src/ServiceQueue.java`, `src/ServiceRequest.java` (menu options 5-7)

- **`ActionStack.java`** - a custom stack (LIFO) built with its own node classes
  (`java.util.Stack` is not used). It holds two stacks:
  - an **action history** stack - `push(action)`, `pop()`, `peek()`, `isEmpty()`, `size()`,
    and `displayRecent()` which prints actions newest first (e.g. "Added student S001",
    "Deleted student S002", "Processed service request #1")
  - a **deleted students** stack - `pushDeleted(student)`, `popDeleted()` and
    `isDeletedStackEmpty()`, used for the **undo last delete** feature
- **`ServiceRequest.java`** - one student service request (auto-generated request ID,
  Student ID, description) with getters and `toString()`.
- **`ServiceQueue.java`** - a custom linked queue (FIFO) using `front` and `rear` pointers
  (`java.util.Queue` is not used).
  - `enqueue(request)` - adds a request at the rear
  - `dequeue()` - removes and returns the request at the front (first come, first served)
  - `peek()`, `isEmpty()`, `size()`, `displayAll()` - helpers
- **Validation:** a service request can only be added for an existing student, the description
  cannot be empty, and processing an empty queue or undoing with nothing deleted shows a
  clear message.
- **Menu options:** 5. Add Service Request, 6. Process Next Service Request,
  7. Display Recent Actions (with the option to undo the last delete).

### Member 3 - BST & Hashing

**Files:** `src/StudentBST.java`, `src/StudentHashTable.java` (menu options 8-9)

- **`StudentBST.java`** - a Binary Search Tree of `Student` objects ordered by Student ID.
  - `insert(student)` - recursive insert (smaller IDs go left, larger go right); rejects duplicates
  - `search(id)` - recursive search that follows only one branch at each level
  - `delete(id)` - handles all three cases: leaf node, node with one child, and node with two
    children (replaced by its in-order successor, the smallest ID in the right subtree)
  - `inOrderDisplay()` - in-order traversal (left, node, right), which prints students
    sorted by Student ID
- **`StudentHashTable.java`** - a hash table with **separate chaining** for fast ID search.
  - Table size 17 (a prime number, to spread the IDs more evenly)
  - `hash(id)` - adds the character codes of the ID and takes the result `mod 17`
  - `put(student)` - inserts at the front of the bucket's linked list; rejects duplicates
  - `get(id)` - hashes the ID and searches only that bucket
  - `remove(id)` - unlinks the student from its bucket
  - `displayTable()` - shows every bucket and the students chained in it
- **Integration:** every student added or deleted through the linked list is also inserted into
  or removed from the BST and the hash table, so all three always hold the same students.
- **Menu options:** 8. Display Students using BST (sorted by ID), 9. Search Student using Hashing.

### Member 4 - Graph

**Files:** `src/CampusGraph.java` (menu options 10-15)

- **`CampusGraph.java`** - the campus modelled as an **undirected graph** stored as an
  **adjacency list**: each location is a vertex, and its list holds the neighbouring locations
  connected to it by a road (edge).
  - `addLocation(name)` - adds a vertex; rejects empty names and duplicates
  - `removeLocation(name)` - removes the vertex and every road connected to it
  - `addConnection(a, b)` - adds a road in both directions; rejects missing locations,
    self-loops and duplicate roads
  - `removeConnection(a, b)` - removes the road in both directions; reports roads that do not exist
  - `displayConnections()` - prints every location with its neighbours
  - `bfs(start)` - **Breadth-First Search** using a queue (visits the nearest locations first)
  - `dfs(start)` - **Depth-First Search** using recursion (goes as deep as possible first)
  - `loadSampleData()` - loads a sample campus (Main Gate, Library, Canteen, Lab Block, Admin,
    Auditorium) when the program starts
- Location names are matched ignoring case, so "library" and "Library" are the same location.
- **Menu options:** 10. Add Location, 11. Remove Location, 12. Add Road, 13. Remove Road,
  14. Display Campus Connections, 15. Traverse using BFS or DFS.

## Data Structures Used

| Requirement | Data structure | Class |
|---|---|---|
| Store and manage student records | Singly linked list | `StudentLinkedList` |
| Recent actions and undo delete | Stack | `ActionStack` |
| Service requests in order of arrival | Queue | `ServiceQueue` |
| Organise students by Student ID | Binary Search Tree | `StudentBST` |
| Fast Student ID search | Hash table (separate chaining) | `StudentHashTable` |
| Campus locations and roads | Graph (adjacency list), BFS and DFS | `CampusGraph` |
