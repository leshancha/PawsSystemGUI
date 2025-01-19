package leshan.pawsshopgui;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SupplyManager {
    
    private ArrayList<PetSupply> supplies;
    private int nextId = 1; // Keeps track of the next available ID, starting from 1
    
    private String[] categories = {
        "Pet Toys", "Harnesses", "Cages", "Grooming Products",
        "Collars", "Food", "Leashes", "Bowls", 
        "Beds", "Treats", "Clothing", "Crates", 
        "Carriers", "Health Products", "Training Supplies", 
        "Scratch Posts", "Aquarium Supplies", "Bird Supplies", 
        "Reptile Supplies", "Pet Cleaning Supplies"
    };

    public SupplyManager() {
        supplies = new ArrayList<>();
        loadSupplies();// Load existing supplies
        
        // Initialize with some sample supplies
        supplies.add(new PetSupply("Rubber Bone", "Pet Toys", 10.99));
        supplies.add(new PetSupply("Dog Harness", "Harnesses", 15.99));
        supplies.add(new PetSupply("Cat Tree", "Cages", 49.99));
        supplies.add(new PetSupply("Pet Shampoo", "Grooming Products", 8.49));
        supplies.add(new PetSupply("Nylon Collar", "Collars", 12.99));
        supplies.add(new PetSupply("Dog Food", "Food", 29.99));
        supplies.add(new PetSupply("Water Bowl", "Bowls", 5.99));
        supplies.add(new PetSupply("Cat Bed", "Beds", 25.99));
        supplies.add(new PetSupply("Dental Chews", "Treats", 14.99));
        supplies.add(new PetSupply("Sweater", "Clothing", 19.99));
        supplies.add(new PetSupply("Travel Crate", "Crates", 39.99));
        supplies.add(new PetSupply("Pet Carrier", "Carriers", 34.99));
        supplies.add(new PetSupply("Flea Treatment", "Health Products", 15.99));
        supplies.add(new PetSupply("Training Clicker", "Training Supplies", 4.99));
        supplies.add(new PetSupply("Cat Litter", "Pet Cleaning Supplies", 10.99));
        // Add more sample supplies as needed
    }

    public void addSupply(PetSupply supply) {
        supplies.add(supply);
        saveSupplies();
    }
    
    public PetSupply createSupply(String name, String category, double price) {
        PetSupply newSupply = new PetSupply(nextId++, name, category, price); // Use nextId and increment it
        addSupply(newSupply);
        return newSupply;
    }

    public ArrayList<PetSupply> getAllSupplies() {
        return supplies;
    }
    
    private void saveSupplies() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("supplies.txt"))) {
            for (PetSupply supply : supplies) {
                writer.write(supply.getName() + "," + supply.getCategory() + "," + supply.getPrice());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void loadSupplies() {
        try (BufferedReader reader = new BufferedReader(new FileReader("supplies.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                String category = parts[1];
                double price = Double.parseDouble(parts[2]);
                supplies.add(new PetSupply(name, category, price));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<PetSupply> searchSuppliesByCategory(String category) {
        ArrayList<PetSupply> result = new ArrayList<>();
        for (PetSupply supply : supplies) {
            if (supply.getCategory().equalsIgnoreCase(category)) {
                result.add(supply);
            }
        }
        return result;
    }
    
    public ArrayList<PetSupply> searchSuppliesByName(String name) {
        ArrayList<PetSupply> result = new ArrayList<>();
        for (PetSupply supply : supplies) {
            if (supply.getName().equalsIgnoreCase(name)) {
                result.add(supply);
            }
        }
        return result;
    }

    public String[] getCategories() {
        // A sample list of categories. Customize as needed.
        return new String[]{"Pet Toys", "Harnesses", "Cages", "Grooming Products", "Collars", "Food", "Beds", "Leashes", "Bowls"};
    }
}