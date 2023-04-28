package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Inventory {
    private ArrayList<Item> items;

    // Konstruktor pro vytvoření inventáře
    public Inventory() {
        items = new ArrayList<>();
    }

    // Přidání položky do inventáře
    public void addItem(Item item) {
        items.add(item);
    }

    // Odebrání položky z inventáře
    public void removeItem(Item item) {
        items.remove(item);
    }

    // Aktualizace položky v inventáři
    public void updateItem(Item oldItem, Item newItem) {
        int index = items.indexOf(oldItem);
        if (index != -1) {
            items.set(index, newItem);
        }
    }

    // Řazení položek v inventáři podle zvoleného sloupce
    public void sortItems(String columnName, boolean ascending) {
        Comparator<Item> comparator = switch (columnName) {
            case "název" -> Comparator.comparing(Item::getName);
            case "cena" -> Comparator.comparing(Item::getPrice);
            case "quantity" -> Comparator.comparing(Item::getQuantity);
            case "kategorie" -> Comparator.comparing(Item::getCategory);
            default -> throw new IllegalArgumentException("Neznámý název sloupce: " + columnName);
        };

        if (!ascending) {
            comparator = comparator.reversed();
        }
        items.sort(comparator);
    }

    // Nastavení seznamu položek v inventáři
    public void setItems(List<Item> items) {
        this.items = new ArrayList<>(items);
    }

    // Filtrování položek podle kategorie
    public List<Item> filterByCategory(String category) {
        return items.stream()
                .filter(item -> item.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    // Získání všech položek v inventáři
    public List<Item> getItems() {
        return new ArrayList<>(items);
    }

    // Kontrola dostupnosti položky v inventáři
    public boolean isAvailable(Item item, int quantity) {
        int index = items.indexOf(item);
        if (index != -1) {
            // Zkontroluje, zda je množství položky v inventáři větší nebo rovno požadovanému množství
            // a zároveň větší než 1
            return items.get(index).getQuantity() >= quantity && quantity >= 1;
        }
        return false;
    }

    // Zvýšení množství položky v inventáři
    public void increaseItemQuantity(Item item, int quantity) {
        int index = items.indexOf(item);
        if (index != -1) {
            Item existingItem = items.get(index);
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        }
    }

    // Snížení množství položky v inventáři
    public void reduceItemQuantity(Item item, int quantity) {
        int index = items.indexOf(item);
        if (index != -1) {
            Item existingItem = items.get(index);
            int newQuantity = Math.max(0, existingItem.getQuantity() - quantity);
            existingItem.setQuantity(newQuantity);
        }

    }

    // Přidání metody getItemQuantity
    public int getItemQuantity(Item item) {
        int index = items.indexOf(item);
        if (index != -1) {
            return items.get(index).getQuantity();
        }
        return 0;
    }

}
