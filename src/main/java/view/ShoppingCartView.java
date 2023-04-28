package view;

import model.Item;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCartView extends JPanel {
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JButton addButton;
    private final JButton removeButton;
    private final JLabel totalLabel;
    private final JButton saveInvoiceButton;


    public ShoppingCartView() {
        setLayout(new BorderLayout());

        // Vytvoření tabulky
        tableModel = new DefaultTableModel(new Object[]{"Název", "Počet"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Vytvoření panelu s tlačítky
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Přidat");
        removeButton = new JButton("Odebrat");
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        add(buttonPanel, BorderLayout.NORTH);

        // Vytvoření panelu s celkovou cenou a tlačítkem pro uložení faktury
        JPanel totalPanel = new JPanel();
        totalPanel.setLayout(new BorderLayout());
        saveInvoiceButton = new JButton("Uložit fakturu");
        totalPanel.add(saveInvoiceButton, BorderLayout.NORTH);
        totalLabel = new JLabel("Celková cena: 0");
        totalPanel.add(totalLabel, BorderLayout.CENTER);
        add(totalPanel, BorderLayout.SOUTH);



    }

    public void setCartItems(HashMap<Item, Integer> cartItems) {
        tableModel.setRowCount(0);
        for (Map.Entry<Item, Integer> entry : cartItems.entrySet()) {
            tableModel.addRow(new Object[]{entry.getKey().getName(), entry.getValue()});
        }
    }

    public void setTotalPrice(double totalPrice) {
        totalLabel.setText("Celková cena: " + totalPrice);
    }

    public Item getSelectedItem() {
        int selectedIndex = table.getSelectedRow();
        if (selectedIndex != -1) {
            String name = (String) tableModel.getValueAt(selectedIndex, 0);
            int quantity = (int) tableModel.getValueAt(selectedIndex, 1);
            return new Item(name, 0, quantity, ""); // Cena a kategorie nejsou zde důležité
        }
        return null;
    }

    public void setAddButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void setRemoveButtonListener(ActionListener listener) {
        removeButton.addActionListener(listener);
    }
    public void setSaveInvoiceButtonListener(ActionListener listener) {
        saveInvoiceButton.addActionListener(listener);
    }

}
