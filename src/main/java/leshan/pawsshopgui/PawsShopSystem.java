package leshan.pawsshopgui;

import javax.swing.SwingUtilities;

public class PawsShopSystem {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPanel().setVisible(true));
    }
}