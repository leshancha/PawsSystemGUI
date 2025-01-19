package leshan.pawsshopgui;

public class PetSupply {
    private String name;
    private String category;
    private double price;

    public PetSupply(String name, String category, double price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    PetSupply(int i, String name, String category, double price) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Category: " + category + ", Price: $" + price;
    }
}