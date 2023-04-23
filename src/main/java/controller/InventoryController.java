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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InventoryController {
    private Inventory inventory;
    private InventoryView view;
    private boolean[] ascendingSort;


    public InventoryController(Inventory inventory, InventoryView view) {
        Deserializer deserializer = new Deserializer();
        this.inventory = inventory;
        this.view = view;
        ascendingSort = new boolean[view.getTable().getColumnCount()];
        Arrays.fill(ascendingSort, true);
        loadItemsFromFile("json");
        setupView();
    }



    private void setupView() {
        view.setItems(inventory.getItems());
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
                } else {
                    JOptionPane.showMessageDialog(view, "Vyberte položku, kterou chcete upravit.", "Chyba", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        view.setRemoveButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Item selectedItem = view.getSelectedItem();
                if (selectedItem != null) {
                    removeItem(selectedItem);
                } else {
                    JOptionPane.showMessageDialog(view, "Vyberte položku, kterou chcete odebrat.", "Chyba", JOptionPane.ERROR_MESSAGE);
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

        view.addMouseListenerToHeader(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int columnIndex = view.getSelectedColumn();
                if (columnIndex != -1) {
                    applySort(columnIndex, ascendingSort[columnIndex]);
                    ascendingSort[columnIndex] = !ascendingSort[columnIndex];
                } else {
                    JOptionPane.showMessageDialog(view, "Vyberte alespon jednu hodnotu sloupce pro řazení.", "Chyba", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        populateCategoryFilter();
    }


    // Přidání položky
    public void addItem(String name, double price, int quantity, String category) {
        Item item = new Item(name, price, quantity, category);
        inventory.addItem(item);
        saveItemsToFile("json");
        view.updateTable(inventory.getItems());
    }

    private void applySort(int columnIndex, boolean ascending) {
        if (columnIndex != -1) {
            String columnName = view.getTable().getColumnName(columnIndex).toLowerCase();
            inventory.sortItems(columnName, ascending);
            view.updateTable(inventory.getItems());
        }
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
        saveItemsToFile("json");
    }


    // Editace položky
    public void updateItem(Item oldItem, String name, double price, int quantity, String category) {
        Item newItem = new Item(name, price, quantity, category);
        inventory.updateItem(oldItem, newItem);
        view.updateTable(inventory.getItems());
        saveItemsToFile("json");
    }


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
