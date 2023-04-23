package controller;

import model.Inventory;
import model.Item;
import model.ShoppingCart;
import view.InventoryView;
import view.ShoppingCartView;


public class ShoppingCartController {
    private final ShoppingCart shoppingCart;
    private final Inventory inventory;
    private final ShoppingCartView view;
    private final InventoryView inventoryView;

    public ShoppingCartController(ShoppingCart shoppingCart, Inventory inventory, ShoppingCartView view, InventoryView inventoryView) {
        this.shoppingCart = shoppingCart;
        this.inventory = inventory;
        this.view = view;
        this.inventoryView = inventoryView;
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
            Item selectedItem = inventoryView.getSelectedItem();
            if (selectedItem != null) {
                addItemToCart(selectedItem, 1); // Přidává pouze jednu jednotku položky do košíku
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
    }

    public void removeItemFromCart(Item item) {
        int quantity = shoppingCart.getCartItems().get(item);
        shoppingCart.removeItem(item);
        inventory.increaseItemQuantity(item, quantity);
    }

}
