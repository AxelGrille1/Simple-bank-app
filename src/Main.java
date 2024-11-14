public class Main {
    public static void main(String[] args) {
        // Create users and an admin
        User user1 = new User(1, "Alice", false, false, 5000.0, false);
        User user2 = new User(2, "Bob", false, false, 1000.0, false);
        Admin admin = new Admin(100, "Admin", 50000.0);

        // Users login
        user1.login(1);
        user2.login(2);

        // Try sending money between users
        System.out.println("\nUser1 (Alice) sends 1000 to User2 (Bob):");
        user1.sendMoney(1000, user2.getAccount(), user2.getName(), admin);

        // View account balances and transaction history
        user1.viewAccount();
        user2.viewAccount();

        // User logouts
        user1.logout();
        user2.logout();

        // Try sending money after logout (should fail)
        System.out.println("\nTry sending money after logout:");
        user1.sendMoney(500, user2.getAccount(), user2.getName(), admin);

        // Admin approves transaction if limit exceeded
        user1.login(1); // Alice logs back in
        user1.viewAccount();
        System.out.println(user1.getName() + " " + user1.isLoggedIn());
        System.out.println(user2.getName() + " " + user2.isLoggedIn());

        System.out.println("\nUser1 (Alice) sends 3000 to User2 (Bob) after exceeding limit:");
        user1.sendMoney(3000, user2.getAccount(), user2.getName(), admin);

        // View Bob's account after approval
        admin.viewAccount(user2.getAccount());
    }
}
