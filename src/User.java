public class User {
    private int userId;
    private String name;
    private BankAccount account;
    private boolean isAdmin;
    private boolean hasFullAccess;
    private int transactionLimit = 3;
    private boolean isLogged;

    public User(int userId, String name, boolean isAdmin, boolean hasFullAccess, double initialBalance, boolean isLogged) {
        this.userId = userId;
        this.name = name;
        this.isAdmin = isAdmin;
        this.hasFullAccess = hasFullAccess;
        this.account = new BankAccount(userId, initialBalance);
        this.isLogged = isLogged;
    }

    public BankAccount getAccount() {
        return account;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public boolean hasFullAccess() {
        return hasFullAccess || isAdmin;
    }

    public int getTransactionLimit() {
        return isAdmin ? Integer.MAX_VALUE : transactionLimit; // Admins have no limit
    }

    public boolean isLoggedIn() {
        return isLogged;
    }

    public void login(int userId) {
        if (!isLogged && this.userId == userId) {
            isLogged = true;
            System.out.println("Welcome " + name + "!");
        } else if (isLogged) {
            System.out.println(name + " is already logged in.");
        } else {
            System.out.println("Login failed. User ID does not match.");
        }
    }

    public void logout() {
        if (isLogged) {
            isLogged = false;
            System.out.println(name + " has logged out.");
        } else {
            System.out.println(name + " is not logged in.");
        }
    }

    // Default method to view the user's own account
    public void viewAccount() {
        if (isLogged) {
            account.displayTransactionHistory();
        } else {
            System.out.println("Please login first.");
        }
    }

    // Overloaded method to view any account if the user is an admin
    public void viewAccount(BankAccount accountToView) {
        if (isAdmin) {
            if (this.isAdmin() || accountToView.getAccountId() == this.account.getAccountId()) {
                accountToView.displayTransactionHistory();
            } else {
                System.out.println("You need admin rights to view other accounts.");
            }
        } else {
            System.out.println("Please login first.");
        }
    }



    public boolean sendMoney(double amount, BankAccount recipientAccount, String recipientName, Admin admin) {
        if (!isLogged) {
            System.out.println("You need to login first.");
            return false;
        }

        if (getTransactionLimit() > 0 || isAdmin()) {
            if (account.transfer(amount, recipientAccount, recipientName)) {
                if (!isAdmin()) {
                    transactionLimit--;
                }
                return true;
            }
        } else {
            if (isAdmin()) {
                System.out.println("Admin approval needed for this transfer.");
                return admin.approveTransaction(this, amount, recipientAccount);
            } else {
                System.out.println("Transaction limit exceeded. Admin approval needed.");
                return false;
            }
        }
        return false;
    }
}
