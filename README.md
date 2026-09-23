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
| Member 1 | _[Name]_ | _[Student ID]_ | Linked list implementation and student-record management |
| Member 2 | _[Name]_ | _[Student ID]_ | Stack and queue implementation and related operations |
| Member 3 | _[Name]_ | _[Student ID]_ | BST/AVL tree implementation and hashing/search functionality |
| Member 4 | _[Name]_ | _[Student ID]_ | Graph implementation, campus locations, connections and BFS/DFS traversal |

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

_To be completed by Member 2._

### Member 3 - BST/AVL & Hashing

_To be completed by Member 3._

### Member 4 - Graph

_To be completed by Member 4._
