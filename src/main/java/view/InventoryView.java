package view;

import controller.InventoryController;
import model.Item;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class InventoryView extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton editButton;
    private JButton removeButton;
    private JComboBox<String> categoryFilter;

    public InventoryView() {
        setLayout(new BorderLayout());

        // Vytvoření tabulky
        tableModel = new DefaultTableModel(new Object[]{"Název", "Cena", "Počet", "Kategorie"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Vytvoření panelu s tlačítky
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Přidat");
        editButton = new JButton("Upravit");
        removeButton = new JButton("Odebrat");
        categoryFilter = new JComboBox<>();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(new JLabel("Filtrovat dle kategorie:"));
        buttonPanel.add(categoryFilter);

        add(buttonPanel, BorderLayout.SOUTH);
    }



    public void setItems(List<Item> items) {
        tableModel.setRowCount(0);
        for (Item item : items) {
            tableModel.addRow(new Object[]{item.getName(), item.getPrice(), item.getQuantity(), item.getCategory()});
        }
    }

    public void addCategoryFilter(String category) {
        categoryFilter.addItem(category);
    }

    public String getSelectedCategoryFilter() {
        return (String) categoryFilter.getSelectedItem();
    }

    public void updateTable() {
        tableModel.fireTableDataChanged();
    }

    public Item getSelectedItem() {
        int selectedIndex = table.getSelectedRow();
        if (selectedIndex != -1) {
            String name = (String) tableModel.getValueAt(selectedIndex, 0);
            double price = (double) tableModel.getValueAt(selectedIndex, 1);
            int quantity = (int) tableModel.getValueAt(selectedIndex, 2);
            String category = (String) tableModel.getValueAt(selectedIndex, 3);
            return new Item(name, price, quantity, category);
        }
        return null;
    }

    public void setEditButtonListener(ActionListener listener) {
        editButton.addActionListener(listener);
    }

    public void setRemoveButtonListener(ActionListener listener) {
        removeButton.addActionListener(listener);
    }

    public void setCategoryFilterListener(ActionListener listener) {
        categoryFilter.addActionListener(listener);
    }

    public void setAddButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

}
