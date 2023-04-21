package view;

import model.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class InventoryView extends JPanel {
    private JTable table;
    private ItemTableModel tableModel;
    private JButton addButton;
    private JButton editButton;
    private JButton removeButton;
    private JComboBox<String> categoryFilter;
    private List<Item> itemList;

    public InventoryView() {
        setLayout(new BorderLayout());

        tableModel = new ItemTableModel();
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

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
        tableModel.setItems(items);
        this.itemList = items;
    }

    public void addCategoryFilter(String category) {
        categoryFilter.addItem(category);
    }

    public String getSelectedCategoryFilter() {
        return (String) categoryFilter.getSelectedItem();
    }

    public void updateTable(List<Item> items) {
        setItems(items);
    }

    public Item getSelectedItem() {
        int selectedIndex = table.getSelectedRow();
        if (selectedIndex != -1 && itemList != null) {
            return itemList.get(selectedIndex);
        }
        return null;
    }

    public void setAddButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);
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

}
