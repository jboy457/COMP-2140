public class ClassList {
    private DNode top;

    /**
     * A node in a doubly linked list, containing data and links to the next and
     * previous nodes.
     */
    private class DNode {
        public Student data;
        public DNode next;

        public DNode(Student data, DNode next) {
            this.data = data;
            this.next = next;
        }
    }

    public ClassList() {
        DNode trailer = new DNode(
                new Student("zzzzzzzz", Integer.MAX_VALUE, null, null),
                null);
        DNode head = new DNode(
                new Student("", Integer.MIN_VALUE, null, null),
                trailer);
        this.top = head;
    }

    /**
     * Checks if the specified student is in the list.
     *
     * @param student The student to search for in the list.
     * @return True if the student is in the list; false otherwise.
     */
    public boolean contains(Student student) {
        boolean isContain = false;
        DNode curr = this.top;
        while (curr.data.id < Integer.MAX_VALUE && curr.data.compareTo(student) < 0) {
            curr = curr.next;
        }

        // Compare students if its equal.
        isContain = curr.data.compareTo(student) == 0;

        return isContain;
    }

    /**
     * Adds a student to the list if they are not already present.
     * 
     * @param student The student to enroll.
     */
    public void enroll(Student student) {
        DNode curr = this.top.next;
        DNode prev = this.top;

        if (this.contains(student))
            return;

        while (curr.data.id < Integer.MAX_VALUE && curr.data.compareTo(student) < 0) {
            prev = curr;
            curr = curr.next;
        }

        prev.next = new DNode(student, curr);
    }

    /**
     * Removes a student from the list if they are present.
     * 
     * @param student The student to unenroll.
     */
    public void unenroll(Student student) {
        DNode curr = this.top.next;
        DNode prev = this.top;

        if (!this.contains(student))
            return;

        while (curr.data.id < Integer.MAX_VALUE && curr.data.compareTo(student) < 0) {
            prev = curr;
            curr = curr.next;
        }

        // Compare students if its equal.
        if (curr.data.compareTo(student) == 0) {
            prev.next = curr.next;
        }
    }

    /**
     * Enrolls all students from another list into this list if they are not already
     * present.
     * This operation does not modify the other list.
     * 
     * @param otherList The list of students to enroll.
     */
    public void enrollAll(ClassList otherList) {
        DNode curr = otherList.top.next;
        while (curr.data.id < Integer.MAX_VALUE) {
            this.enroll(curr.data);
            curr = curr.next;
        }
    }

    /**
     * Unenrolls all students in the specified list from this list if they are
     * present.
     * This operation does not modify the other list.
     * 
     * @param otherList The list of students to unenroll.
     */
    public void unenrollAll(ClassList otherList) {
        DNode curr = otherList.top.next;
        while (curr.data.id < Integer.MAX_VALUE) {
            this.unenroll(curr.data);
            curr = curr.next;
        }
    }

    /**
     * Creates and returns a deep copy of the current student list.
     * 
     * @return A new ClassList instance that is a deep copy of this list.
     */
    public ClassList duplicate() {
        ClassList newList = new ClassList();

        DNode curr = this.top.next;
        while (curr.data.id < Integer.MAX_VALUE) {
            newList.enroll(curr.data);
            curr = curr.next;
        }

        return newList;
    }

    /**
     * Returns a new ClassList with students enrolled in both this list and
     * otherList.
     *
     * @param otherList The ClassList to compare with.
     * @return A new ClassList containing students in both lists.
     */
    public ClassList alsoEnrolledIn(ClassList otherList) {
        ClassList newList = new ClassList();
        DNode curr = otherList.top.next;
        while (curr.data.id < Integer.MAX_VALUE) {
            if (this.contains(curr.data)) {
                newList.enroll(curr.data);
            }
            curr = curr.next;
        }
        return newList;
    }

    /**
     * Returns a new ClassList with students enrolled only in this list and not in
     * otherList.
     *
     * @param otherList The ClassList to compare with.
     * @return A new ClassList containing students unique to this list.
     */
    public ClassList notAlsoEnrolledIn(ClassList otherList) {
        ClassList newList = new ClassList();
        DNode curr = this.top.next;

        // Traverse the list
        while (curr.data.id < Integer.MAX_VALUE) {
            if (!otherList.contains(curr.data)) {
                newList.enroll(curr.data);
            }
            curr = curr.next;
        }

        return newList;
    }

    /**
     * Prints the elements of the list to the standard output.
     */
    public void print() {
        DNode curr = this.top.next;

        while (curr.data.id < Integer.MAX_VALUE) {
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
