package leshan.pawsshopgui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PawsShopGUI extends JFrame {
    private String userType;
    private SupplyManager supplyManager;

    public PawsShopGUI(String userType) {
        this.userType = userType;
        this.supplyManager = new SupplyManager();

        setTitle("Paws Shop - " + userType + " Panel");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a panel with a resizable background
        BackgroundPanel backgroundPanel = new BackgroundPanel("C:/Users/lesha/OneDrive/Desktop/Assignment/CSE4006-Object Oriented Programming/paw-print.jpg");
        backgroundPanel.setLayout(new GridBagLayout());

        // Create and style buttons
        JButton viewSuppliesButton = createStyledButton("View All Supplies", Color.ORANGE, Color.DARK_GRAY, 20);
        JButton searchSuppliesButton = createStyledButton("Search Supplies by Category", Color.ORANGE, Color.DARK_GRAY, 20);
        JButton addSuppliesButton = createStyledButton("Add Supplies", Color.ORANGE, Color.DARK_GRAY, 20);
        JButton logoutButton = createStyledButton("Logout", Color.ORANGE, Color.DARK_GRAY, 20);

        // Add action listeners
        viewSuppliesButton.addActionListener(e -> viewSupplies());
        searchSuppliesButton.addActionListener(e -> searchSuppliesByCategory());
        addSuppliesButton.addActionListener(e -> addSupplies());
        logoutButton.addActionListener(e -> logout());

        JLabel titleLabel = new JLabel(userType + " Panel", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 50));
        titleLabel.setForeground(Color.GRAY);

        // Arrange components using GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        backgroundPanel.add(titleLabel, gbc);
        gbc.gridy++;
        backgroundPanel.add(viewSuppliesButton, gbc);
        gbc.gridy++;
        backgroundPanel.add(searchSuppliesButton, gbc);
        gbc.gridy++;
        backgroundPanel.add(addSuppliesButton, gbc);

        if (userType.equals("Manager")) {
            JButton createUserButton = createStyledButton("Create Cashier Account", Color.ORANGE, Color.DARK_GRAY, 30);
            createUserButton.addActionListener(e -> createCashierAccount());
            gbc.gridy++;
            backgroundPanel.add(createUserButton, gbc);
        }

        gbc.gridy++;
        backgroundPanel.add(logoutButton, gbc);

        // Set backgroundPanel as the content pane and refresh the UI
        setContentPane(backgroundPanel);
        revalidate();
        repaint();
    }

    // Methods for viewing, searching, adding supplies, etc. (remain unchanged)

    private class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String filePath) {
            try {
                backgroundImage = new ImageIcon(filePath).getImage();
            } catch (Exception e) {
                e.printStackTrace();
            }
            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    repaint(); // Repaint when the window is resized
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                int width = getWidth();
                int height = getHeight();
                g.drawImage(backgroundImage, 0, 0, width, height, this); // Scale image to fit panel size
            }
        }
    }
    
    private void viewSupplies() {
        String[] columnNames = {"Name", "Category", "Price"};
        ArrayList<PetSupply> supplies = supplyManager.getAllSupplies();
        Object[][] data = new Object[supplies.size()][3];

        for (int i = 0; i < supplies.size(); i++) {
            PetSupply supply = supplies.get(i);
            data[i][0] = supply.getName();
            data[i][1] = supply.getCategory();
            data[i][2] = supply.getPrice();
        }

        JTable table = new JTable(new DefaultTableModel(data, columnNames));
        JScrollPane scrollPane = new JScrollPane(table);

        JOptionPane.showMessageDialog(this, scrollPane, "All Supplies", JOptionPane.PLAIN_MESSAGE);
    }

    private void searchSuppliesByCategory() {
        String[] categories = supplyManager.getCategories();
        JComboBox<String> categoryComboBox = new JComboBox<>(categories);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Select Category:"), BorderLayout.NORTH);
        panel.add(categoryComboBox, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(this, panel, "Search Supplies", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String selectedCategory = (String) categoryComboBox.getSelectedItem();
            showSuppliesByCategory(selectedCategory);
        }
    }

    private void showSuppliesByCategory(String category) {
        ArrayList<PetSupply> supplies = supplyManager.searchSuppliesByCategory(category);

        String[] columnNames = {"Name", "Category", "Price"};
        Object[][] data = new Object[supplies.size()][3];

        for (int i = 0; i < supplies.size(); i++) {
            PetSupply supply = supplies.get(i);
            data[i][0] = supply.getName();
            data[i][1] = supply.getCategory();
            data[i][2] = supply.getPrice();
        }

        JTable table = new JTable(new DefaultTableModel(data, columnNames));
        JScrollPane scrollPane = new JScrollPane(table);

        JOptionPane.showMessageDialog(this, scrollPane, "Supplies in Category: " + category, JOptionPane.PLAIN_MESSAGE);
    }

    private void addSupplies() {
        JTextField nameField = new JTextField(20);
        JTextField categoryField = new JTextField(20);
        JTextField priceField = new JTextField(20);

        JPanel addSupplyPanel = new JPanel(new GridLayout(3, 2));
        addSupplyPanel.add(new JLabel("Name:"));
        addSupplyPanel.add(nameField);
        addSupplyPanel.add(new JLabel("Category:"));
        addSupplyPanel.add(categoryField);
        addSupplyPanel.add(new JLabel("Price:"));
        addSupplyPanel.add(priceField);

        int result = JOptionPane.showConfirmDialog(this, addSupplyPanel, "Add New Supply", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                String category = categoryField.getText();
                double price = Double.parseDouble(priceField.getText());
                PetSupply newSupply = new PetSupply(name, category, price);
                supplyManager.addSupply(newSupply);
                JOptionPane.showMessageDialog(this, "Supply added successfully.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void createCashierAccount() {
        String username = JOptionPane.showInputDialog("Enter new cashier username:");
        String password = JOptionPane.showInputDialog("Enter new cashier password:");
        if (username != null && password != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("cashiers.txt", true))) {
                writer.write(username + "," + password);
                writer.newLine();
                JOptionPane.showMessageDialog(this, "Cashier account created successfully.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving cashier account.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void logout() {
        dispose();
        new LoginPanel().setVisible(true);
    }

    private JButton createStyledButton(String text, Color bgColor, Color borderColor, int cornerRadius) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setFont(new Font("Courier", Font.BOLD, 15));
        button.setBorder(BorderFactory.createLineBorder(borderColor, 0));
        button.setFocusPainted(false);
        button.setFocusPainted(false);
        // Set rounded border
        button.setBorder(new RoundedBorder(cornerRadius, borderColor));

        // Make the button background transparent
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        
        return button;
    }
    private class buttonPanel extends JPanel {
        private Image setContentPane;

        public buttonPanel(String filePath, Image JLabel) {
            try {
                JLabel = new ImageIcon(filePath).getImage();
                addComponentListener(new ComponentAdapter() {
                    @Override
                    public void componentResized(ComponentEvent e) {
                        repaint(); // Repaint when the window is resized
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
} 

