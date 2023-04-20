package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Inventory {
    private ArrayList<Item> items;

    public Inventory() {
        items = new ArrayList<>();
    }

    // Přidání položky
    public void addItem(Item item) {
        items.add(item);
    }

    // Odebrání položky
    public void removeItem(Item item) {
        items.remove(item);
    }

    // Editace položky
    public void updateItem(Item oldItem, Item newItem) {
        int index = items.indexOf(oldItem);
        if (index != -1) {
            items.set(index, newItem);
        }
    }

    // Řazení položek podle sloupce
    public void sortItems(String columnName, boolean ascending) {
        Comparator<Item> comparator;

        switch (columnName) {
            case "název":
                comparator = Comparator.comparing(Item::getName);
                break;
            case "cena":
                comparator = Comparator.comparing(Item::getPrice);
                break;
            case "počet":
                comparator = Comparator.comparing(Item::getQuantity);
                break;
            case "kategorie":
                comparator = Comparator.comparing(Item::getCategory);
                break;
            default:
                throw new IllegalArgumentException("Neznámý název sloupce: " + columnName);
        }

        if (!ascending) {
            comparator = comparator.reversed();
        }

        items.sort(comparator);
    }

    // Nastavení seznamu položek
    public void setItems(List<Item> items) {
        this.items = new ArrayList<>(items);
    }

    // Filtrování podle kategorie
    public List<Item> filterByCategory(String category) {
        return items.stream()
                .filter(item -> item.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    // Získání všech položek
    public List<Item> getItems() {
        return new ArrayList<>(items);
    }

    // Kontrola dostupnosti položky
    public boolean isAvailable(Item item, int quantity) {
        int index = items.indexOf(item);
        if (index != -1) {
            return items.get(index).getQuantity() >= quantity;
        }
        return false;
    }

    // Zvýšení množství položky
    public void increaseItemQuantity(Item item, int quantity) {
        int index = items.indexOf(item);
        if (index != -1) {
            Item existingItem = items.get(index);
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        }
    }

    // Snížení množství položky
    public void reduceItemQuantity(Item item, int quantity) {
        int index = items.indexOf(item);
        if (index != -1) {
            Item existingItem = items.get(index);
            int newQuantity = Math.max(0, existingItem.getQuantity() - quantity);
            existingItem.setQuantity(newQuantity);
        }
    }
}
