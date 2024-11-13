public class Admin extends User {

    public Admin(int userId, String name, double initialBalance) {
        super(userId, name, true, true, initialBalance, false);
    }

    public boolean approveTransaction(User user, double amount, BankAccount recipientAccount) {
        System.out.println("Admin has approved the transaction.");
        return recipientAccount.deposit(amount, "Approved Transfer from Admin");
    }
}
