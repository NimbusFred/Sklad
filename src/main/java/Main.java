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
            // Vytvoří nové objekty pro zobrazení inventáře (InventoryView) a nákupního košíku (ShoppingCartView)
            InventoryView inventoryView = new InventoryView();
            ShoppingCartView shoppingCartView = new ShoppingCartView();

            // Vytvoření nových řadičů (controllers) pro inventář a nákupní košík, které propojují model a zobrazení
            // Bez ukládání do proměnných, protože v třídě Main nejsou potřeba
            new InventoryController(inventory, inventoryView);
            new ShoppingCartController(new ShoppingCart(), inventory, shoppingCartView, inventoryView);

            // Vytvoření hlavního okna aplikace, které zobrazuje InventoryView a ShoppingCartView
            MainFrame mainFrame = new MainFrame(inventoryView, shoppingCartView);
            // Nastavení, aby se aplikace neukončila automaticky po zavření hlavního okna
            mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            // Přidání posluchače pro událost zavření hlavního okna
            mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    // Zobrazení dialogového okna s možností potvrdit ukončení aplikace
                    int confirmExit = JOptionPane.showConfirmDialog(
                            mainFrame,
                            "Máte neuložené změny, opravdu chcete ukončit aplikaci?",
                            "Ukončit",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );
                    // Pokud uživatel potvrdí ukončení aplikace, ukončí se celý program
                    if (confirmExit == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
            });
            // Nastavení hlavního okna jako viditelné, aby se zobrazilo uživateli
            mainFrame.setVisible(true);
        });
    }


}