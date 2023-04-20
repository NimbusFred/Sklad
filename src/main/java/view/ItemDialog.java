package view;

import model.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ItemDialog extends JDialog {
    private Item item;
    private boolean confirmed = false;

    private JTextField nameField;
    private JTextField priceField;
    private JTextField quantityField;
    private JTextField categoryField;
    private JButton confirmButton;
    private JButton cancelButton;

    public ItemDialog(JFrame parent, String title, Item item) {
        super(parent, title, true);
        this.item = item;

        setLayout(new GridLayout(5, 2));

        nameField = new JTextField(item.getName());
        priceField = new JTextField(String.valueOf(item.getPrice()));
        quantityField = new JTextField(String.valueOf(item.getQuantity()));
        categoryField = new JTextField(item.getCategory());

        confirmButton = new JButton("OK");
        cancelButton = new JButton("Zrušit");

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmed = true;
                item.setName(nameField.getText());
                item.setPrice(Double.parseDouble(priceField.getText()));
                item.setQuantity(Integer.parseInt(quantityField.getText()));
                item.setCategory(categoryField.getText());
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmed = false;
                dispose();
            }
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
