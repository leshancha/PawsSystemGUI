package leshan.pawsshopgui;

public class Cashier extends User {
    public Cashier(String username, String password) {
        super(username, password);
    }

    @Override
    public void displayMenu() {
        System.out.println("Cashier Menu:");
        // Additional cashier functionality
    }
}