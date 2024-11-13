import java.util.ArrayList;
import java.util.List;
public class BankAccount {
    private int accountId;
    private double balance;
    private List<Transaction> transactionHistory;

    public BankAccount(int accountId, double balance) {
        this.accountId = accountId;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
    }

    public boolean deposit(double amount, String description) {
        if (amount <= 0) {
            System.out.println("Invalid deposit amount.");
            return false;
        }
        balance += amount;
        transactionHistory.add(new Transaction(amount, description));
        return true;
    }

    public boolean withdraw(double amount, String description) {
        if (amount <= 0 || amount > balance) {
            System.out.println("Insufficient funds or invalid amount.");
            return false;
        }
        balance -= amount;
        transactionHistory.add(new Transaction(-amount, description));
        return true;
    }

    public boolean transfer(double amount, BankAccount recipientAccount, String recipientName) {
        if (withdraw(amount, "Transfer to " + recipientName)) {
            boolean depositSuccess = recipientAccount.deposit(amount, "Transfer from " + recipientName);
            if (!depositSuccess) {
                deposit(amount, "Rollback Transfer to " + recipientName);
                return false;
            }
            return true;
        }
        return false;
    }


    public void displayTransactionHistory() {
        System.out.println("Transaction History for Account ID: " + accountId);
        for (Transaction transaction : transactionHistory) {
            System.out.println(transaction.getDescription() + ": " + transaction.getAmount());
        }
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }
    // Getters and Setters for balance and accountId are assumed.
}
