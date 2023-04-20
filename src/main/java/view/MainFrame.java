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

    public MainFrame(Inventory inventory) {
        setTitle("Sklad a nákupní košík");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Inicializace modelů
        ShoppingCart shoppingCart = new ShoppingCart();

        // Inicializace zobrazení
        inventoryView = new InventoryView();
        shoppingCartView = new ShoppingCartView();

        // Inicializace controllerů
        InventoryController inventoryController = new InventoryController(inventory, inventoryView);
        ShoppingCartController shoppingCartController = new ShoppingCartController(shoppingCart, inventory, shoppingCartView);

        // Propojení controllerů a zobrazení
        inventoryController.bindView(inventoryView);
        shoppingCartController.bindView(shoppingCartView);

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
