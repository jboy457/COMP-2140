public class Student implements Comparable<Student> {
    String username;
    int id;
    String firstName;
    String lastName;

    public Student(String username, int id, String firstName, String lastName) {
        this.username = username;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int compareTo(Student student) {
        return this.username.compareTo(student.username);
    }   
}
