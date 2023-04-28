package view;

import model.Item;

import javax.swing.*;
import java.awt.*;

public class ItemDialog extends JDialog {
    private final Item item;
    private boolean confirmed = false;

    private final JTextField nameField;
    private final JTextField priceField;
    private final JTextField quantityField;
    private final JTextField categoryField;

    public ItemDialog(JFrame parent, String title, Item item) {
        super(parent, title, true);
        this.item = item;

        setLayout(new GridLayout(5, 2));

        nameField = new JTextField(item.getName());
        priceField = new JTextField(String.valueOf(item.getPrice()));
        quantityField = new JTextField(String.valueOf(item.getQuantity()));
        categoryField = new JTextField(item.getCategory());

        JButton confirmButton = new JButton("OK");
        JButton cancelButton = new JButton("Zrušit");

        confirmButton.addActionListener(e -> {
            if (nameField.getText().isEmpty() || priceField.getText().isEmpty() || quantityField.getText().isEmpty() || categoryField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(ItemDialog.this, "Prosím vyplňte všechna pole.", "Chyba", JOptionPane.ERROR_MESSAGE);
            } else {
                confirmed = true;
                item.setName(nameField.getText());
                item.setPrice(Double.parseDouble(priceField.getText()));
                item.setQuantity(Integer.parseInt(quantityField.getText()));
                item.setCategory(categoryField.getText());
                dispose();
            }
        });


        cancelButton.addActionListener(e -> {
            confirmed = false;
            dispose();
        });

        add(new JLabel("Název:"));
        add(nameField);
        add(new JLabel("Cena:"));
        add(priceField);
        add(new JLabel("Počet:"));
        add(quantityField);
        add(new JLabel("Kategorie:"));
        add(categoryField);
        add(confirmButton);
        add(cancelButton);

        pack();
        setLocationRelativeTo(parent);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public Item getItem() {
        return item;
    }
}
