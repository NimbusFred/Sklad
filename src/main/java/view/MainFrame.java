package view;

import controller.InventoryController;
import controller.ShoppingCartController;
import model.Inventory;
import model.ShoppingCart;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private InventoryView inventoryView;
    private ShoppingCartView shoppingCartView;

    public MainFrame(InventoryView inventoryView, ShoppingCartView shoppingCartView) {
        setTitle("Sklad a nákupní košík");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Přiřazení zobrazení
        this.inventoryView = inventoryView;
        this.shoppingCartView = shoppingCartView;

        // Přidání zobrazení do okna
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, inventoryView, shoppingCartView);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(400);
        add(splitPane, BorderLayout.CENTER);

        // Nastavení velikosti okna a zobrazení
        setSize(800, 600);
        setLocationRelativeTo(null);
    }
}
