package controller;

import model.Inventory;
import model.Item;
import view.InventoryView;

import java.util.List;

public class InventoryController {
    private Inventory inventory;
    private InventoryView view;

    public InventoryController(Inventory inventory, InventoryView inventoryView) {
        this.inventory = inventory;
    }

    public void bindView(InventoryView view) {
        this.view = view;
        setupView();
    }

    private void setupView() {

    }



    // Přidání položky
    public void addItem(String name, double price, int quantity, String category) {
        Item item = new Item(name, price, quantity, category);
        inventory.addItem(item);
        view.updateTable();
    }

    // Odebrání položky
    public void removeItem(Item item) {
        inventory.removeItem(item);
        view.updateTable();
    }

    // Editace položky
    public void updateItem(Item oldItem, String name, double price, int quantity, String category) {
        Item newItem = new Item(name, price, quantity, category);
        inventory.updateItem(oldItem, newItem);
        view.updateTable();
    }

    // Řazení položek podle sloupce
    public void sortItems(String columnName, boolean ascending) {
        inventory.sortItems(columnName, ascending);
        view.updateTable();
    }

    // Filtrování podle kategorie
    public List<Item> filterByCategory(String category) {
        return inventory.filterByCategory(category);
    }

    // Získání všech položek
    public List<Item> getItems() {
        return inventory.getItems();
    }
}
