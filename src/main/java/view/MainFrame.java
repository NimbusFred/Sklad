package view;

import controller.InventoryController;
import controller.ShoppingCartController;
import model.Inventory;
import model.ShoppingCart;
import util.Deserializer;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private InventoryController inventoryController;
    private final Deserializer deserializer;

    public MainFrame() {
        setTitle("Sklad a nákupní košík");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        deserializer = new Deserializer();

        // Inicializace zobrazení a řadičů
        initControllers(new Inventory(), new ShoppingCart());
    }

    private void initControllers(Inventory inventory, ShoppingCart shoppingCart) {
        // Vytvoření nových zobrazení a řadičů
        InventoryView inventoryView = new InventoryView();
        ShoppingCartView shoppingCartView = new ShoppingCartView();
        inventoryController = new InventoryController(inventory, inventoryView);
        ShoppingCartController shoppingCartController = new ShoppingCartController(shoppingCart, inventory, shoppingCartView, inventoryView);

        // Přidání zobrazení do okna
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, inventoryView, shoppingCartView);
        splitPane.setOneTouchExpandable(true);

        int windowWidth = 800; // Nastavená šířka okna
        int dividerLocation = (int) (windowWidth * (2.0 / 3.0));
        splitPane.setDividerLocation(dividerLocation);

        splitPane.setResizeWeight(0.5);
        splitPane.setContinuousLayout(true);
        getContentPane().removeAll();
        add(splitPane, BorderLayout.CENTER);

        // Nastavení velikosti okna a zobrazení
        setSize(windowWidth, 600);
        setLocationRelativeTo(null);

        revalidate();
        repaint();
    }

}
