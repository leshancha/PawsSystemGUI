package leshan.pawsshopgui;

public class Manager extends Cashier {
    public Manager(String username, String password) {
        super(username, password);
    }

    public void createCashier(String cashierUsername, String cashierPassword) {
        Cashier newCashier = new Cashier(cashierUsername, cashierPassword);
        System.out.println("New cashier created: " + cashierUsername);
        // Implementation to store the cashier user
    }

    @Override
    public void displayMenu() {
        super.displayMenu();
        System.out.println("Manager Menu: Additional manager functionality");
    }
}