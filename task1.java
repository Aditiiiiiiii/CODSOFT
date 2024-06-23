import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
    private int score;
    private int roundsWon;
    private int totalAttempts;

    public NumberGuessingGame() {
        this.score = 0;
        this.roundsWon = 0;
        this.totalAttempts = 0;
    }

    public void playRound() {
        Random random = new Random();
        int numberToGuess = random.nextInt(100) + 1;
        int attempts = 0;
        int maxAttempts = 6;

        System.out.println("Welcome to the number guessing game!");
        System.out.println("I'm thinking of a number between 1 and 100.");
        System.out.println("You have " + maxAttempts + " attempts to guess it.");

        Scanner scanner = new Scanner(System.in);

        while (attempts < maxAttempts) {
            System.out.print("Enter your guess: ");
            int userGuess = scanner.nextInt();
            attempts++;

            if (userGuess == numberToGuess) {
                System.out.println(" Congratulations! You guessed the number in " + attempts + " attempts.");
                this.score += maxAttempts - attempts;
                this.roundsWon++;
                break;
            } else if (userGuess < numberToGuess) {
                System.out.println("Your guess is too low. Try again!");
            } else {
                System.out.println("Your guess is too high. Try again!");
            }
        }

        if (attempts == maxAttempts) {
            System.out.println("Sorry, you didn't guess the number. It was " + numberToGuess);
        }

        this.totalAttempts += attempts;
    }

    public void playGame() {
        while (true) {
            this.playRound();

            System.out.print("Do you want to play again? (yes/no): ");
            Scanner scanner = new Scanner(System.in);
            String playAgain = scanner.next();

            if (!playAgain.equalsIgnoreCase("yes")) {
                break;
            }
        }

        System.out.println("Your final score is " + this.score + " out of " + this.totalAttempts);
        System.out.println("You won " + this.roundsWon + " rounds.");
    }

    public static void main(String[] args) {
        NumberGuessingGame game = new NumberGuessingGame();
        game.playGame();
    }
}