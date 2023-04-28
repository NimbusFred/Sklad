package view;

import model.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class InventoryView extends JPanel {
    private final JTable table;
    private final ItemTableModel tableModel;
    private final JButton addButton;
    private final JButton editButton;
    private final JButton removeButton;
    private final JComboBox<String> categoryFilter;
    private List<Item> itemList;

    public InventoryView() {
        setLayout(new BorderLayout());

        tableModel = new ItemTableModel();
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
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
        setMinimumSize(new Dimension(400, 600));
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

    public int getSelectedColumn() {
        return table.getSelectedColumn();
    }

    public JTable getTable() {
        return table;
    }

    public Item getSelectedItem() {
        int selectedIndex = table.getSelectedRow();
        if (selectedIndex != -1 && itemList != null) {
            return itemList.get(selectedIndex);
        }
        return null;
    }

    public List<Item> getSelectedItems() {
        int[] selectedIndices = table.getSelectedRows();
        List<Item> selectedItems = new ArrayList<>();
        if (selectedIndices.length > 0 && itemList != null) {
            for (int index : selectedIndices) {
                selectedItems.add(itemList.get(index));
            }
        }
        return selectedItems;
    }

    public void clearCategoryFilter() {
        categoryFilter.removeAllItems();
    }
    public void enableItem(Item item) {
        int rowIndex = itemList.indexOf(item);
        if (rowIndex != -1) {
            table.setEnabled(true);
        }
    }

    public void setCategoryFilter(Set<String> categories) {
        categoryFilter.removeAllItems();
        for (String category : categories) {
            categoryFilter.addItem(category);
        }
    }
    public void setSelectedCategoryFilter(String category) {
        categoryFilter.setSelectedItem(category);
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

    public void addMouseListenerToHeader(MouseAdapter adapter) {
        table.getTableHeader().addMouseListener(adapter);
    }


}
