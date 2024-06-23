import java.util.*;
import java.util.concurrent.*;

// QuizQuestion class to store each quiz question with options and correct answer
class QuizQuestion {
    private String question;
    private List<String> options;
    private int correctAnswerIndex;

    public QuizQuestion(String question, List<String> options, int correctAnswerIndex) {
        this.question = question;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
}

// QuizManager class to manage the quiz, questions, timer, and scoring
public class QuizManager {
    private List<QuizQuestion> quizQuestions;
    private int currentQuestionIndex;
    private int score;
    private Scanner scanner;
    private ScheduledExecutorService executorService;
    private boolean answerSubmitted;

    public QuizManager() {
        this.quizQuestions = new ArrayList<>();
        this.currentQuestionIndex = 0;
        this.score = 0;
        this.scanner = new Scanner(System.in);
        this.executorService = Executors.newScheduledThreadPool(1);
    }

    public void addQuestion(String question, List<String> options, int correctAnswerIndex) {
        QuizQuestion quizQuestion = new QuizQuestion(question, options, correctAnswerIndex);
        quizQuestions.add(quizQuestion);
    }

    public void startQuiz() {
        for (QuizQuestion quizQuestion : quizQuestions) {
            answerSubmitted = false;
            presentQuestionWithTimer(quizQuestion);
            waitForAnswer();
        }
        executorService.shutdown(); // Shutdown the executor after quiz ends
        displayResult();
    }

    private void presentQuestionWithTimer(QuizQuestion quizQuestion) {
        System.out.println("Question: " + quizQuestion.getQuestion());
        List<String> options = quizQuestion.getOptions();
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }

        // Timer for 30 seconds
        executorService.schedule(() -> {
            if (!answerSubmitted) {
                System.out.println("\nTime's up! You didn't submit an answer for this question.");
                System.out.println("Correct answer: " + options.get(quizQuestion.getCorrectAnswerIndex()));
                System.out.println();
                answerSubmitted = true; // Mark answer as submitted automatically
            }
        }, 30, TimeUnit.SECONDS);
    }

    private void waitForAnswer() {
        System.out.print("Your answer (1-" + quizQuestions.get(currentQuestionIndex).getOptions().size() + "): ");
        int userAnswer = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (userAnswer <= 0 || userAnswer > quizQuestions.get(currentQuestionIndex).getOptions().size()) {
            System.out.println("Invalid option. Skipping question.");
            return;
        }

        int correctAnswerIndex = quizQuestions.get(currentQuestionIndex).getCorrectAnswerIndex();
        if (userAnswer - 1 == correctAnswerIndex) {
            System.out.println("Correct!");
            score++;
        } else {
            System.out.println("Incorrect. Correct answer is: " + quizQuestions.get(currentQuestionIndex).getOptions().get(correctAnswerIndex));
        }
        answerSubmitted = true; // Mark answer as submitted
        System.out.println();
        currentQuestionIndex++;
    }

    private void displayResult() {
        System.out.println("Quiz ended. Here are your results:");
        System.out.println("Score: " + score + " out of " + quizQuestions.size());

        System.out.println("\nSummary of Questions:");
        for (int i = 0; i < quizQuestions.size(); i++) {
            QuizQuestion quizQuestion = quizQuestions.get(i);
            System.out.println("Question " + (i + 1) + ": " + quizQuestion.getQuestion());
            List<String> options = quizQuestion.getOptions();
            System.out.println("Options:");
            for (int j = 0; j < options.size(); j++) {
                System.out.println((j + 1) + ". " + options.get(j));
            }
            int correctAnswerIndex = quizQuestion.getCorrectAnswerIndex();
            System.out.println("Correct Answer: " + options.get(correctAnswerIndex));
            System.out.println();
        }
    }

    public static void main(String[] args) {
        QuizManager quizManager = new QuizManager();

        // Adding sample quiz questions
        quizManager.addQuestion("What is the capital of France?", Arrays.asList("London", "Paris", "Berlin", "Rome"), 1);
        quizManager.addQuestion("Who wrote 'Romeo and Juliet'?", Arrays.asList("Charles Dickens", "William Shakespeare", "Jane Austen", "Leo Tolstoy"), 1);
        quizManager.addQuestion("What is the powerhouse of the cell?", Arrays.asList("Mitochondria", "Nucleus", "Ribosome", "Golgi apparatus"), 0);

        // Starting the quiz
        quizManager.startQuiz();
    }
}
