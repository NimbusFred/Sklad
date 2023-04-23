import controller.InventoryController;
import controller.ShoppingCartController;
import model.Inventory;
import model.ShoppingCart;
import view.InventoryView;
import view.MainFrame;
import view.ShoppingCartView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        SwingUtilities.invokeLater(() -> {
            InventoryView inventoryView = new InventoryView();
            ShoppingCartView shoppingCartView = new ShoppingCartView();
            InventoryController inventoryController = new InventoryController(inventory, inventoryView);
            ShoppingCartController shoppingCartController = new ShoppingCartController(new ShoppingCart(), inventory, shoppingCartView);
            MainFrame mainFrame = new MainFrame(inventoryView, shoppingCartView);
            mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    int confirmExit = JOptionPane.showConfirmDialog(
                            mainFrame,
                            "Máte neuložené změny, opravdu chcete ukončit aplikaci?",
                            "Ukončit",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );
                    if (confirmExit == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
            });
            mainFrame.setVisible(true);
        });
    }
}