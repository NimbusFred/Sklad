package controller;

import model.Inventory;
import model.Item;
import model.ShoppingCart;
import view.ShoppingCartView;

import java.util.HashMap;

public class ShoppingCartController {
    private ShoppingCart shoppingCart;
    private Inventory inventory;
    private ShoppingCartView view;


    public ShoppingCartController(ShoppingCart shoppingCart, Inventory inventory, ShoppingCartView shoppingCartView) {
        this.shoppingCart = shoppingCart;
        this.inventory = inventory;
    }

    // Přidání položky do nákupního košíku
    public void addItemToCart(Item item, int quantity) {
        if (inventory.isAvailable(item, quantity)) {
            shoppingCart.addItem(item, quantity);
            inventory.reduceItemQuantity(item, quantity);
        }
    }


    public void bindView(ShoppingCartView view) {
        this.view = view;
        setupView();
    }
    private void setupView() {
        // Nastavení obsluhy událostí a aktualizace zobrazení
    }


    // Odebrání položky z nákupního košíku
    public void removeItemFromCart(Item item) {
        int quantity = shoppingCart.getCartItems().get(item);
        shoppingCart.removeItem(item);
        inventory.increaseItemQuantity(item, quantity);
    }

    // Změna množství položky v nákupním košíku
    public void updateItemQuantityInCart(Item item, int newQuantity) {
        int currentQuantity = shoppingCart.getCartItems().get(item);
        int difference = newQuantity - currentQuantity;

        if (inventory.isAvailable(item, difference)) {
            shoppingCart.updateItemQuantity(item, newQuantity);
            inventory.reduceItemQuantity(item, difference);
        }
    }

    // Získání celkové ceny nákupního košíku
    public double getCartTotalPrice() {
        return shoppingCart.getTotalPrice();
    }

    // Získání položek nákupního košíku
    public HashMap<Item, Integer> getCartItems() {
        return shoppingCart.getCartItems();
    }
}
