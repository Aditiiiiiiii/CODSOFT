import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Course class to store course information
class Course {
    private String code;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    private int enrolledStudents;

    public Course(String code, String title, String description, int capacity, String schedule) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.enrolledStudents = 0;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getSchedule() {
        return schedule;
    }

    public int getEnrolledStudents() {
        return enrolledStudents;
    }

    public void enrollStudent() {
        enrolledStudents++;
    }

    public void dropStudent() {
        enrolledStudents--;
    }

    public boolean isFull() {
        return enrolledStudents >= capacity;
    }

    @Override
    public String toString() {
        return "Course Code: " + code + "\nTitle: " + title + "\nDescription: " + description + "\nSchedule: " + schedule
                + "\nCapacity: " + capacity + "\nEnrolled Students: " + enrolledStudents + "\n";
    }
}

// Student class to store student information
class Student {
    private int studentId;
    private String name;
    private List<String> registeredCourses;

    public Student(int studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public List<String> getRegisteredCourses() {
        return registeredCourses;
    }

    public void registerCourse(String courseCode) {
        registeredCourses.add(courseCode);
    }

    public void dropCourse(String courseCode) {
        registeredCourses.remove(courseCode);
    }

    @Override
    public String toString() {
        return "Student ID: " + studentId + "\nName: " + name + "\nRegistered Courses: " + registeredCourses + "\n";
    }
}

// Course Management System class to manage courses and students
public class CourseManagementSystem {
    private Map<String, Course> courses;
    private Map<Integer, Student> students;
    private int nextStudentId;

    public CourseManagementSystem() {
        this.courses = new HashMap<>();
        this.students = new HashMap<>();
        this.nextStudentId = 1; // Starting student ID
    }

    public void addCourse(String code, String title, String description, int capacity, String schedule) {
        Course course = new Course(code, title, description, capacity, schedule);
        courses.put(code, course);
    }

    public void addStudent(String name) {
        Student student = new Student(nextStudentId, name);
        students.put(nextStudentId, student);
        nextStudentId++;
    }

    public void displayCourses() {
        System.out.println("Available Courses:");
        for (Course course : courses.values()) {
            System.out.println(course);
        }
    }

    public void displayStudents() {
        System.out.println("Registered Students:");
        for (Student student : students.values()) {
            System.out.println(student);
        }
    }

    public void registerStudentForCourse(int studentId, String courseCode) {
        Student student = students.get(studentId);
        Course course = courses.get(courseCode);

        if (student == null) {
            System.out.println("Student with ID " + studentId + " not found.");
            return;
        }

        if (course == null) {
            System.out.println("Course with code " + courseCode + " not found.");
            return;
        }

        if (course.isFull()) {
            System.out.println("Course " + course.getTitle() + " is already full. Registration failed.");
            return;
        }

        student.registerCourse(courseCode);
        course.enrollStudent();
        System.out.println("Student " + student.getName() + " registered successfully for course " + course.getTitle());
    }

    public void dropCourseForStudent(int studentId, String courseCode) {
        Student student = students.get(studentId);
        Course course = courses.get(courseCode);

        if (student == null) {
            System.out.println("Student with ID " + studentId + " not found.");
            return;
        }

        if (course == null) {
            System.out.println("Course with code " + courseCode + " not found.");
            return;
        }

        if (!student.getRegisteredCourses().contains(courseCode)) {
            System.out.println("Student " + student.getName() + " is not registered for course " + course.getTitle());
            return;
        }

        student.dropCourse(courseCode);
        course.dropStudent();
        System.out.println("Student " + student.getName() + " dropped course " + course.getTitle() + " successfully.");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CourseManagementSystem cms = new CourseManagementSystem();

        // Adding sample courses
        cms.addCourse("CS101", "Introduction to Computer Science", "Fundamentals of programming and algorithms", 30, "Mon/Wed/Fri 9:00-10:30");
        cms.addCourse("ENG201", "English Composition", "Writing and communication skills", 25, "Tue/Thu 11:00-12:30");
        cms.addCourse("MATH301", "Calculus", "Mathematical analysis and limits", 20, "Mon/Wed 14:00-15:30");

        // Adding sample students
        cms.addStudent("Alice");
        cms.addStudent("Bob");
        cms.addStudent("Charlie");

        while (true) {
            System.out.println("\nCourse Management System Menu:");
            System.out.println("1. Display available courses");
            System.out.println("2. Display registered students");
            System.out.println("3. Register student for a course");
            System.out.println("4. Drop course for a student");
            System.out.println("5. Exit");

            System.out.print("Enter your option: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    cms.displayCourses();
                    break;
                case 2:
                    cms.displayStudents();
                    break;
                case 3:
                    System.out.print("Enter student ID: ");
                    int studentId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter course code to register: ");
                    String courseCodeToRegister = scanner.nextLine().trim();
                    cms.registerStudentForCourse(studentId, courseCodeToRegister);
                    break;
                case 4:
                    System.out.print("Enter student ID: ");
                    int studentIdToDrop = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter course code to drop: ");
                    String courseCodeToDrop = scanner.nextLine().trim();
                    cms.dropCourseForStudent(studentIdToDrop, courseCodeToDrop);
                    break;
                case 5:
                    System.out.println("Exiting Course Management System. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}
