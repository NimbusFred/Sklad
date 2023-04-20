package model;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private HashMap<Item, Integer> cartItems;

    public ShoppingCart() {
        cartItems = new HashMap<>();
    }

    // Přidání položky
    public void addItem(Item item, int quantity) {
        cartItems.put(item, cartItems.getOrDefault(item, 0) + quantity);
    }

    // Odebrání položky
    public void removeItem(Item item) {
        cartItems.remove(item);
    }

    // Změna množství položky
    public void updateItemQuantity(Item item, int newQuantity) {
        if (newQuantity > 0) {
            cartItems.put(item, newQuantity);
        } else {
            cartItems.remove(item);
        }
    }

    // Získání celkové ceny nákupního košíku
    public double getTotalPrice() {
        double totalPrice = 0;
        for (Map.Entry<Item, Integer> entry : cartItems.entrySet()) {
            totalPrice += entry.getKey().getPrice() * entry.getValue();
        }
        return totalPrice;
    }

    // Získání položek nákupního košíku
    public HashMap<Item, Integer> getCartItems() {
        return new HashMap<>(cartItems);
    }
}
