package model;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private final HashMap<Item, Integer> cartItems;

    public ShoppingCart() {
        cartItems = new HashMap<>();
    }

    // Přidání položky
    public void addItem(Item item, int quantity) {
        cartItems.put(item, cartItems.getOrDefault(item, 0) + quantity);
    }

    // Odebrání zadaného množství položek
    public void removeItem(Item item, int quantityToRemove) {
        Integer currentQuantity = cartItems.get(item);
        if (currentQuantity != null) {
            // Pokud je množství k odebrání menší než aktuální množství, aktualizujte množství
            if (quantityToRemove < currentQuantity) {
                cartItems.put(item, currentQuantity - quantityToRemove);
            } else {
                // V opačném případě položku z košíku úplně odeberte
                cartItems.remove(item);
            }
        }
    }

    public Item getItemByName(String name) {
        for (Item item : cartItems.keySet()) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
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
