package controller;

import model.Inventory;
import model.Item;
import util.Deserializer;
import util.Serializer;
import view.InventoryView;
import view.ItemDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
        // Nastavení akčních listenerů pro tlačítka
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

    public void loadItemsFromFile(String filePath, String format) {
        Deserializer deserializer = new Deserializer(filePath);
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
            view.updateTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveItemsToFile(String filePath, String format) {
        Serializer serializer = new Serializer(filePath);
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
