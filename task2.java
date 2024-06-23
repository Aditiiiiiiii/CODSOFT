import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FailingSubjectList {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        final double PASSING_MARKS = 30.0;

        int numSubjects;
        List<String> failingSubjects = new ArrayList<>();
        int totalMarks = 0;
        double averagePercentage;

        // Input number of subjects
        System.out.print("Enter the number of subjects: ");
        numSubjects = scanner.nextInt();

        // Input marks for each subject and calculate total marks
        for (int i = 1; i <= numSubjects; i++) {
            System.out.print("Enter marks obtained in subject " + i + " (out of 100): ");
            int marks = scanner.nextInt();
            totalMarks += marks;

            // Check if the subject is failing
            if (marks < PASSING_MARKS) {
                failingSubjects.add("Subject " + i);
            }
        }

        // Calculate average percentage
        averagePercentage = (double) totalMarks / numSubjects;

        // Display results
        System.out.println("\nTotal Marks: " + totalMarks);
        System.out.println("Average Percentage: " + averagePercentage);

        if (!failingSubjects.isEmpty()) {
            System.out.println("Failing Subjects:");
            for (String subject : failingSubjects) {
                System.out.println(subject);
            }
        } else {
            System.out.println("No subject is failing.");
        }

        // Close scanner
        scanner.close();
    }
}
