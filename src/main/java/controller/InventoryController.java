package controller;

import model.Inventory;
import model.Item;
import util.Deserializer;
import util.Serializer;
import view.InventoryView;
import view.ItemDialog;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.*;

public class InventoryController {
    private final Inventory inventory;
    private final InventoryView view;
    private final boolean[] ascendingSort;

    // Konstruktor pro třídu InventoryController
    public InventoryController(Inventory inventory, InventoryView view) {
        this.inventory = inventory;
        this.view = view;
        ascendingSort = new boolean[view.getTable().getColumnCount()];
        Arrays.fill(ascendingSort, true);
        String dataFormat = showDataFormatSelectionDialog();
        loadItemsFromFile(dataFormat);
        setupView();
    }

    // Zobrazí dialog pro výběr formátu dat
    private String showDataFormatSelectionDialog() {
        Object[] options = {"JSON", "CSV"};
        int choice = JOptionPane.showOptionDialog(null,
                "Vyberte formát dat pro načítání:",
                "Výběr formátu dat",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        return choice == 0 ? "json" : "csv";
    }

    // Nastavení akcí pro tlačítka a další komponenty ve view
    private void setupView() {
        view.setItems(inventory.getItems());
        view.setAddButtonListener(e -> {
            ItemDialog itemDialog = new ItemDialog(null, "Přidat položku", new Item("", 0, 0, ""));
            itemDialog.setVisible(true);
            if (itemDialog.isConfirmed()) {
                addItem(itemDialog.getItem().getName(), itemDialog.getItem().getPrice(), itemDialog.getItem().getQuantity(), itemDialog.getItem().getCategory());
            }
        });

        view.setEditButtonListener(e -> {
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
        });

        view.setRemoveButtonListener(e -> {
            Item selectedItem = view.getSelectedItem();
            if (selectedItem != null) {
                removeItem(selectedItem);
            } else {
                JOptionPane.showMessageDialog(view, "Vyberte položku, kterou chcete odebrat.", "Chyba", JOptionPane.ERROR_MESSAGE);
            }
        });

        view.setCategoryFilterListener(e -> {
            String selectedCategory = view.getSelectedCategoryFilter();
            view.setItems(filterByCategory(selectedCategory));
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

    // Aktualizace filtru kategorií
    private void updateCategoryFilter() {
        Set<String> categories = new HashSet<>();
        categories.add("Nic nefiltruj");
        for (Item item : inventory.getItems()) {
            categories.add(item.getCategory());
        }
        view.setCategoryFilter(categories);
    }

    // Řazení položek podle vybraného sloupce
    private void applySort(int columnIndex, boolean ascending) {
        if (columnIndex != -1) {
            String columnName = view.getTable().getColumnName(columnIndex).toLowerCase();
            inventory.sortItems(columnName, ascending);
            view.updateTable(inventory.getItems());
        }
    }

    // Naplnění filtru kategorií dostupnými hodnotami
    private void populateCategoryFilter() {
        Set<String> categories = new TreeSet<>();
        for (Item item : inventory.getItems()) {
            categories.add(item.getCategory());
        }

        view.clearCategoryFilter();
        view.addCategoryFilter("Nic nefiltruj");
        for (String category : categories) {
            view.addCategoryFilter(category);
        }
        view.setSelectedCategoryFilter("Nic nefiltruj");
    }

    // Přidání položky
    public void addItem(String name, double price, int quantity, String category) {
        Item item = new Item(name, price, quantity, category);
        inventory.addItem(item);
        saveItemsToFile(showDataFormatSelectionDialog());
        view.updateTable(inventory.getItems());
        updateCategoryFilter();
    }

    // Odebrání položky
    public void removeItem(Item item) {
        inventory.removeItem(item);
        view.updateTable(inventory.getItems());
        saveItemsToFile(showDataFormatSelectionDialog());
        updateCategoryFilter();
    }

    // Editace položky
    public void updateItem(Item oldItem, String name, double price, int quantity, String category) {
        Item newItem = new Item(name, price, quantity, category);
        inventory.updateItem(oldItem, newItem);
        view.updateTable(inventory.getItems());
        saveItemsToFile(showDataFormatSelectionDialog());
        updateCategoryFilter();
    }

    // Filtrování podle kategorie
    public List<Item> filterByCategory(String category) {
        if (category == null || category.equalsIgnoreCase("Nic nefiltruj")) {
            return inventory.getItems();
        }
        return inventory.filterByCategory(category);
    }

    // Načítání položek ze souboru
    public void loadItemsFromFile(String format) {
        Deserializer deserializer = new Deserializer();
        List<Item> items;
        try {
            if (format.equalsIgnoreCase("json")) {
                items = deserializer.deserializeFromJson();
            } else if (format.equalsIgnoreCase("csv")) {
                items = deserializer.deserializeFromCSV();
            } else {
                throw new IllegalArgumentException("Unsupported file format: " + format);
            }
            inventory.setItems(items);
            view.updateTable(inventory.getItems());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Ukládání položek do souboru
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