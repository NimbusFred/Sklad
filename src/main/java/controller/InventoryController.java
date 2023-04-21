package controller;

import model.Inventory;
import model.Item;
import util.Deserializer;
import util.Serializer;
import view.InventoryView;
import view.ItemDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InventoryController {
    private Inventory inventory;
    private InventoryView view;


    public InventoryController(Inventory inventory, InventoryView view) {
        Deserializer deserializer = new Deserializer();
        this.inventory = inventory;
        this.view = view;
        setupView();
        loadItemsFromFile("json");
        deserializer.loadItemsToInventory(inventory);
    }


    private void setupView() {
        // Nastavení akčních listenerů pro tlačítka
        view.setItems(inventory.getItems());
        populateCategoryFilter();
        view.setAddButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ItemDialog itemDialog = new ItemDialog(null, "Přidat položku", new Item("", 0, 0, ""));
                itemDialog.setVisible(true);
                if (itemDialog.isConfirmed()) {
                    addItem(itemDialog.getItem().getName(), itemDialog.getItem().getPrice(), itemDialog.getItem().getQuantity(), itemDialog.getItem().getCategory());
                }
            }
        });

        view.setEditButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Item oldItem = view.getSelectedItem();
                if (oldItem != null) {
                    ItemDialog itemDialog = new ItemDialog(null, "Upravit položku", oldItem);
                    itemDialog.setVisible(true);
                    if (itemDialog.isConfirmed()) {
                        updateItem(oldItem, itemDialog.getItem().getName(), itemDialog.getItem().getPrice(), itemDialog.getItem().getQuantity(), itemDialog.getItem().getCategory());
                    }
                }
            }
        });

        view.setRemoveButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Item selectedItem = view.getSelectedItem();
                if (selectedItem != null) {
                    removeItem(selectedItem);
                }
            }
        });

        view.setCategoryFilterListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = view.getSelectedCategoryFilter();
                view.setItems(filterByCategory(selectedCategory));
            }
        });
    }


    // Přidání položky
    public void addItem(String name, double price, int quantity, String category) {
        Item item = new Item(name, price, quantity, category);
        inventory.addItem(item);
        saveItemsToFile("json");
        view.updateTable(inventory.getItems());
    }

    private void populateCategoryFilter() {
        Set<String> categories = new HashSet<>();
        for (Item item : inventory.getItems()) {
            categories.add(item.getCategory());
        }
        for (String category : categories) {
            view.addCategoryFilter(category);
        }
    }

    // Odebrání položky
    public void removeItem(Item item) {
        inventory.removeItem(item);
        view.updateTable(inventory.getItems());
    }


    // Editace položky
    public void updateItem(Item oldItem, String name, double price, int quantity, String category) {
        Item newItem = new Item(name, price, quantity, category);
        inventory.updateItem(oldItem, newItem);
        view.updateTable(inventory.getItems());
    }


    // Řazení položek podle sloupce
    /*public void sortItems(String columnName, boolean ascending) {
        inventory.sortItems(columnName, ascending);
        view.updateTable();
    }*/

    // Filtrování podle kategorie
    public List<Item> filterByCategory(String category) {
        return inventory.filterByCategory(category);
    }

    // Získání všech položek
    public List<Item> getItems() {
        return inventory.getItems();
    }

    public void loadItemsFromFile(String format) {
        Deserializer deserializer = new Deserializer();
        List<Item> items;
        try {
            if (format.equalsIgnoreCase("json")) {
                items = deserializer.deserializeFromJson();
            } else if (format.equalsIgnoreCase("csv")) {
                items = deserializer.deserializeFromJson();
            } else {
                throw new IllegalArgumentException("Unsupported file format: " + format);
            }
            inventory.setItems(items);
            view.updateTable(inventory.getItems());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void saveItemsToFile(String format) {
        Serializer serializer = new Serializer();
        try {
            if (format.equalsIgnoreCase("json")) {
                serializer.serializeToJson(inventory.getItems());
            } else if (format.equalsIgnoreCase("csv")) {
                serializer.serializeToCsv(inventory.getItems());
            } else {
                throw new IllegalArgumentException("Unsupported file format: " + format);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
