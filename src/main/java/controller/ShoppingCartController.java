package controller;

import model.Inventory;
import model.Item;
import model.ShoppingCart;
import util.Serializer;
import view.InventoryView;
import view.ShoppingCartView;

import java.util.ArrayList;
import java.util.List;


public class ShoppingCartController {
    private final ShoppingCart shoppingCart;
    private final Inventory inventory;
    private final ShoppingCartView view;
    private final InventoryView inventoryView;
    private final Serializer serializer;


    public ShoppingCartController(ShoppingCart shoppingCart, Inventory inventory, ShoppingCartView view, InventoryView inventoryView) {
        this.shoppingCart = shoppingCart;
        this.inventory = inventory;
        this.view = view;
        this.inventoryView = inventoryView;
        this.serializer = new Serializer();
        setupView();
    }

    public void addItemToCart(Item item, int quantity) {
        if (inventory.isAvailable(item, quantity) && item.getQuantity() > 0) {
            shoppingCart.addItem(item, quantity);
            inventory.reduceItemQuantity(item, quantity);
            inventoryView.disableItem(item);
        }
    }


    private void setupView() {
        // Nastavení obsluhy událostí a aktualizace zobrazení
        view.setAddButtonListener(e -> {
            System.out.println("mačkám!");
            Item selectedItem = inventoryView.getSelectedItem();
            if (selectedItem != null) {
                addItemToCart(selectedItem, selectedItem.getQuantity()); // Přidává pouze položky s množstvím 1 nebo vyšším do košíku
                view.setCartItems(shoppingCart.getCartItems());
                view.setTotalPrice(shoppingCart.getTotalPrice());
            }
        });

        view.setRemoveButtonListener(e -> {
            Item selectedItem = view.getSelectedItem();
            if (selectedItem != null) {
                removeItemFromCart(selectedItem);
                inventoryView.enableItem(selectedItem);
                view.setCartItems(shoppingCart.getCartItems());
                view.setTotalPrice(shoppingCart.getTotalPrice());
            }
        });

        view.setSaveItemsButtonListener(e -> {
            List<Item> items = new ArrayList<>(shoppingCart.getCartItems().keySet());
            try {
                serializer.serializeToJson(items);
                // serializer.serializeToCsv(items); // pro uložení do CSV formátu
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

    }


    public void removeItemFromCart(Item item) {
        Integer quantity = shoppingCart.getCartItems().get(item);

        if (quantity != null) {
            shoppingCart.removeItem(item);
            inventory.increaseItemQuantity(item, quantity);
        }
    }


}
