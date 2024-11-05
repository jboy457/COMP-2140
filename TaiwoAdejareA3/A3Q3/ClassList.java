public class ClassList {
    private int tableSize; // Size of table
    private TNode[] hashTables;

    public ClassList() {
        this.tableSize = 137; // Prime Number for table size.
        this.hashTables = new TNode[this.tableSize];
    }

    /**
     * Represents a node in the linked list used in the hash table, containing
     * student data
     * and a reference to the next node in the list.
     */
    private class TNode {
        public Student data;
        public TNode next;

        public TNode(Student data, TNode next) {
            this.data = data;
            this.next = next;
        }
    }

    /**
     * Computes the hash code for a given student based on their username.
     *
     * @param student The student for whom to generate the hash code.
     * @return The hash code, an integer representing the hashed value.
     */
    private int hashCode(Student student) {
        final int PRIME_MULTI = 31;
        int[] keyArr = keyToIntArr(student.username);

        int result = keyArr[0] % tableSize;
        for (int i = 1; i < keyArr.length; i++) {
            result = (PRIME_MULTI * result + keyArr[i]) % tableSize;
        }

        return result;
    }

    /**
     * Converts a string key into an array of integer values, where each element
     * represents the numeric value of a character in the key.
     *
     * @param key The string key to convert.
     * @return An array of integers corresponding to the numeric values of each
     *         character in the key.
     */
    private int[] keyToIntArr(String key) {
        int keyLength = key.length();
        int[] keyArr = new int[keyLength];
        for (int i = 0; i < keyLength; i++) {
            keyArr[i] = Character.getNumericValue(key.charAt(i));
        }
        return keyArr;
    }

    /**
     * Checks if the specified student is contained within the hash table.
     *
     * @param student The student to check for in the hash table.
     * @return True if the student is found, otherwise false.
     */
    public boolean contains(Student student) {
        Boolean isContain = false;
        int hashCode = this.hashCode(student);

        TNode curr = this.hashTables[hashCode];

        while (curr != null && !isContain) {
            // Compare students if its equal.
            isContain = curr.data.compareTo(student) == 0;
            curr = curr.next;
        }

        return isContain;
    }

    /**
     * Enrolls a student by adding them to the hash table if they are not already
     * enrolled.
     *
     * @param student The student to enroll.
     */
    public void enroll(Student student) {
        if (!this.contains(student)) {
            int hashCode = this.hashCode(student);
            this.hashTables[hashCode] = new TNode(student, this.hashTables[hashCode]);
        }
    }

    /**
     * Unenrolls a student by removing them from the hash table if they are
     * currently enrolled.
     *
     * @param student The student to unenroll.
     */
    public void unenroll(Student student) {
        if (this.contains(student)) {
            int hashCode = this.hashCode(student);

            TNode curr = this.hashTables[hashCode];
            TNode prev = null;
            while (curr != null && curr.data.compareTo(student) != 0) {
                prev = curr;
                curr = curr.next;
            }

            if (curr != null) {
                if (prev != null) {
                    prev.next = curr.next;
                } else {
                    this.hashTables[hashCode] = curr.next;
                }
            }
        }
    }

    /**
     * Enrolls all students from another class list into the current class list.
     *
     * @param otherList The class list containing students to be enrolled.
     */
    public void enrollAll(ClassList otherList) {
        for (TNode otherTable : otherList.hashTables) {
            TNode curr = otherTable;
            while (curr != null) {
                this.enroll(curr.data);
                curr = curr.next;
            }
        }
    }

    /**
     * Unenrolls all students from another class list in the current class list.
     *
     * @param otherList The class list containing students to be unenrolled.
     */
    public void unenrollAll(ClassList otherList) {
        for (TNode otherTable : otherList.hashTables) {
            TNode curr = otherTable;
            while (curr != null) {
                this.unenroll(curr.data);
                curr = curr.next;
            }
        }
    }

    /**
     * Creates a duplicate of the current class list, copying the hash table
     * structure
     * but not the actual student data to avoid shared references.
     *
     * @return A new ClassList object that is a duplicate of the current class list.
     */
    public ClassList duplicate() {
        ClassList newClass = new ClassList();

        for (int i = 0; i < this.hashTables.length; i++) {
            if (this.hashTables[i] != null) {
                newClass.hashTables[i] = this.hashTables[i];
            }
        }

        return newClass;
    }

    /**
     * Creates a new class list containing students who are enrolled in both the
     * current
     * class list and the specified other class list.
     *
     * @param otherList The class list to compare against.
     * @return A new ClassList object containing students enrolled in both class
     *         lists.
     */
    public ClassList alsoEnrolledIn(ClassList otherList) {
        ClassList newClass = new ClassList();

        for (TNode otherTable : otherList.hashTables) {
            TNode curr = otherTable;
            while (curr != null) {
                if (this.contains(curr.data)) {
                    newClass.enroll(curr.data);
                }
                curr = curr.next;
            }
        }

        return newClass;
    }

    /**
     * Creates a new class list containing students who are enrolled in the current
     * class list
     * but not in the specified other class list.
     *
     * @param otherList The class list to compare against.
     * @return A new ClassList object containing students not enrolled in the other
     *         class list.
     */
    public ClassList notAlsoEnrolledIn(ClassList otherList) {
        ClassList newClass = new ClassList();

        for (TNode thisTable : this.hashTables) {
            TNode curr = thisTable;
            while (curr != null) {
                if (!otherList.contains(curr.data)) {
                    newClass.enroll(curr.data);
                }
                curr = curr.next;

            }
        }

        return newClass;
    }

    /**
     * Prints the details of all students enrolled in the class list, including
     * their ID,
     * first name, last name, and username.
     */
    public void print() {
        for (TNode thisTable : this.hashTables) {
            TNode curr = thisTable;

            while (curr != null) {
                System.out.println("====================================");
                System.out.printf("Student ID: %d\n", curr.data.id);
                System.out.println("====================================");
                System.out.printf("Firstname: %s\n", curr.data.firstName);
                System.out.printf("Lastname: %s\n", curr.data.lastName);
                System.out.printf("Username: %s\n\n", curr.data.username);
                curr = curr.next;
            }
        }
    }

}
