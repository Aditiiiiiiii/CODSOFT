import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankAccount {
    private String name;
    private double balance;
    private List<String> transactionHistory;

    public BankAccount(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
    }

    public BankAccount(String name) {
        this(name, 0); // Default balance is 0
    }

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            this.transactionHistory.add("Deposit: $" + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount <= this.balance) {
            this.balance -= amount;
            this.transactionHistory.add("Withdraw: $" + amount);
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public void transfer(BankAccount toAccount, double amount) {
        if (amount <= this.balance) {
            this.withdraw(amount);
            toAccount.deposit(amount);
            this.transactionHistory.add("Transfer: $" + amount + " to " + toAccount.name);
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public void checkBalance() {
        System.out.println("Available balance: $" + this.balance);
    }

    public void displayTransactionHistory() {
        System.out.println("Transaction History:");
        for (int i = 0; i < this.transactionHistory.size(); i++) {
            System.out.println((i + 1) + ". " + this.transactionHistory.get(i));
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        BankAccount account1 = new BankAccount("User 1");
        BankAccount account2 = new BankAccount("User 2");

        while (true) {
            System.out.println("\nATM Menu:");
            System.out.println("1. Check balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Transaction history");
            System.out.println("6. Exit");

            System.out.print("Enter your option: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    account1.checkBalance();
                    break;
                case 2:
                    System.out.print("Enter the deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    account1.deposit(depositAmount);
                    break;
                case 3:
                    System.out.print("Enter the withdrawal amount: ");
                    double withdrawAmount = scanner.nextDouble();
                    account1.withdraw(withdrawAmount);
                    break;
                case 4:
                    System.out.print("Enter the transfer amount: ");
                    double transferAmount = scanner.nextDouble();
                    account1.transfer(account2, transferAmount);
                    break;
                case 5:
                    account1.displayTransactionHistory();
                    break;
                case 6:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}
