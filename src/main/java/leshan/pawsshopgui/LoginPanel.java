package leshan.pawsshopgui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class LoginPanel extends JFrame {
    private JTextField userText;
    private JPasswordField passwordText;
    // Manager credentials
    private final String MANAGER_USERNAME = "OOP";
    private final String MANAGER_PASSWORD = "119";
    private Map<String, String> cashierCredentials = new HashMap<>(); // For cashier auto-login
    private String userType;

    public LoginPanel() {
        setTitle("Paws Shop - Login");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        loadCashierCredentials(); // Load stored cashier credentials

        // Create background panel with auto-scaling image
        BackgroundPanel backgroundPanel = new BackgroundPanel("C:/Users/lesha/OneDrive/Desktop/Assignment/CSE4006-Object Oriented Programming/9711700.jpg");
        setContentPane(backgroundPanel);
        backgroundPanel.setLayout(new GridBagLayout());

        // Create Login Buttons
        JButton loginCashierButton = createStyledButton("Login as Cashier", Color.ORANGE, Color.DARK_GRAY, 10);
        JButton loginManagerButton = createStyledButton("Login as Manager", Color.ORANGE, Color.DARK_GRAY, 10);

        // Action listeners for login buttons
        loginCashierButton.addActionListener(e -> showLoginFields("Cashier"));
        loginManagerButton.addActionListener(e -> showLoginFields("Manager"));

        // Add buttons to the layout in two rows
        backgroundPanel.setLayout(new BorderLayout());

        // Add the "PET STORE" label at the top
        JLabel titleLabel = new JLabel("PAWS SHOP", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 50));
        titleLabel.setForeground(Color.GRAY);
        backgroundPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Create a panel for the buttons and use GridBagLayout inside it
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false); // Make button panel transparent to see the background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(loginManagerButton, gbc);
        gbc.gridy = 1;
        buttonPanel.add(loginCashierButton, gbc);

        // Add the button panel to the bottom of the main panel
        backgroundPanel.add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void showLoginFields(String userType) {
        getContentPane().removeAll(); // Clear the panel
        BackgroundPanel backgroundPanel = new BackgroundPanel("C:/Users/lesha/OneDrive/Desktop/Assignment/CSE4006-Object Oriented Programming/pawsmanager.jpg");
        setContentPane(backgroundPanel);
        backgroundPanel.setLayout(new GridBagLayout());

        // Create Login Fields
        JLabel userLabel = new JLabel("Username:");
        userText = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        passwordText = new JPasswordField(20);
        
        JButton loginButton = createStyledButton("Login", Color.ORANGE, Color.BLACK, 10);
        loginButton.addActionListener(e -> login(userType));
        JButton backButton = createStyledButton("Back", Color.ORANGE, Color.BLACK, 10);
        backButton.addActionListener(e -> new LoginPanel().setVisible(true));
        
        backgroundPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        backgroundPanel.add(userLabel, gbc);
        gbc.gridx = 1;
        backgroundPanel.add(userText, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        backgroundPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        backgroundPanel.add(passwordText, gbc);
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        backgroundPanel.add(loginButton, gbc);
        gbc.gridy = 3;
        backgroundPanel.add(backButton, gbc);

        revalidate();
        repaint();
    }
    
    private void login(String userType) {
        String username = userText.getText();
        String password = new String(passwordText.getPassword());
        if (userType.equals("Manager")) {
            if (username.equals(MANAGER_USERNAME) && password.equals(MANAGER_PASSWORD)) {
                new PawsShopGUI(userType).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid manager credentials.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            if (cashierCredentials.containsKey(username) && cashierCredentials.get(username).equals(password)) {
                new PawsShopGUI(userType).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid cashier credentials.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadCashierCredentials() {
        try (BufferedReader br = new BufferedReader(new FileReader("cashiers.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) cashierCredentials.put(parts[0], parts[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    
    

    // Custom JPanel for dynamic background scaling
    private class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String filePath) {
            try {
                backgroundImage = new ImageIcon(filePath).getImage();
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

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginPanel::new);
    }
}