package controller;

import model.Inventory;
import model.Item;
import model.ShoppingCart;
import view.InventoryView;
import view.ShoppingCartView;

import javax.swing.*;
import java.util.List;
import java.util.Map;

public class ShoppingCartController {
    private final ShoppingCart shoppingCart;
    private final Inventory inventory;
    private final ShoppingCartView view;
    private final InventoryView inventoryView;

    public ShoppingCartController(ShoppingCart shoppingCart, Inventory inventory, ShoppingCartView view, InventoryView inventoryView) {
        this.shoppingCart = shoppingCart;
        this.inventory = inventory;
        this.view = view;
        this.inventoryView = inventoryView;
        setupView();
    }

    // Nastavení pohledu
    private void setupView() {
        // Nastavení obsluhy událostí a aktualizace zobrazení
        view.setAddButtonListener(e -> {
            List<Item> selectedItems = inventoryView.getSelectedItems();
            if (!selectedItems.isEmpty()) {
                // Zde získejte množství, které chce uživatel přidat do košíku
                String input = JOptionPane.showInputDialog(view, "Zadejte množství položek, které chcete přidat do košíku:", "Přidat množství", JOptionPane.PLAIN_MESSAGE);

                if (input != null && !input.isEmpty()) {
                    try {
                        int quantityToAdd = Integer.parseInt(input);
                        if (quantityToAdd <= 0) {
                            JOptionPane.showMessageDialog(view, "Množství musí být kladné číslo.", "Chyba", JOptionPane.ERROR_MESSAGE);
                        } else {
                            for (Item selectedItem : selectedItems) {
                                int availableQuantity = inventory.getItemQuantity(selectedItem);
                                if (quantityToAdd <= availableQuantity) {
                                    addItemToCart(selectedItem, quantityToAdd);
                                } else {
                                    JOptionPane.showMessageDialog(view, "Nelze přidat více položek, než je dostupných. Dostupné množství: " + availableQuantity, "Chyba", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                            view.setCartItems(shoppingCart.getCartItems());
                            view.setTotalPrice(shoppingCart.getTotalPrice());
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(view, "Zadejte platné číslo.", "Chyba", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        view.setRemoveButtonListener(e -> {
            String selectedItemName = view.getSelectedItemName();
            if (selectedItemName != null) {
                Item selectedItem = shoppingCart.getItemByName(selectedItemName);
                if (selectedItem != null) {
                    // Zde získejte množství, které chce uživatel odebrat z košíku
                    String input = JOptionPane.showInputDialog(view, "Zadejte množství položek, které chcete odebrat z košíku:", "Odebrat množství", JOptionPane.PLAIN_MESSAGE);

                    if (input != null && !input.isEmpty()) {
                        try {
                            int quantityToRemove = Integer.parseInt(input);
                            if (quantityToRemove <= 0) {
                                JOptionPane.showMessageDialog(view, "Množství musí být kladné číslo.", "Chyba", JOptionPane.ERROR_MESSAGE);
                            } else {
                                // Zkontrolujte, zda je množství k odebrání menší nebo rovno aktuálnímu množství v košíku
                                Integer currentQuantity = shoppingCart.getCartItems().getOrDefault(selectedItem, 0);
                                if (quantityToRemove <= currentQuantity) {
                                    removeItemFromCart(selectedItem, quantityToRemove); // Předá množství k odebrání do metody
                                    inventoryView.enableItem(selectedItem);
                                    view.setCartItems(shoppingCart.getCartItems());
                                    view.setTotalPrice(shoppingCart.getTotalPrice());
                                } else {
                                    JOptionPane.showMessageDialog(view, "Nelze odebrat více položek, než je v košíku.", "Chyba", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(view, "Zadejte platné číslo.", "Chyba", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        view.setSaveInvoiceButtonListener(e -> printInvoice());
    }

    // Přidání položky do košíku
    public void addItemToCart(Item item, int quantity) {
        if (inventory.isAvailable(item, quantity) && quantity > 0) {
            shoppingCart.addItem(item, quantity);
            inventory.reduceItemQuantity(item, quantity);
            view.setCartItems(shoppingCart.getCartItems());
        }
    }

    // Tisk faktury
    public void printInvoice() {
        System.out.println("===== Faktura =====");
        double totalPrice = 0;
        for (Map.Entry<Item, Integer> entry : shoppingCart.getCartItems().entrySet()) {
            Item item = entry.getKey();
            int quantity = entry.getValue();
            double itemTotalPrice = item.getPrice() * quantity;
            totalPrice += itemTotalPrice;
            System.out.println("Název: " + item.getName() + ", Počet kusů: " + quantity + ", Cena za kus: " + item.getPrice() + ", Celkem za položku: " + itemTotalPrice);
        }
        System.out.println("Celková cena: " + totalPrice);
        System.out.println("===================");
    }

    // Odebrání položky z košíku
    public void removeItemFromCart(Item item, int quantityToRemove) {
        Integer quantity = shoppingCart.getCartItems().get(item);
        System.out.println(quantity);
        if (quantity != null) {
            shoppingCart.removeItem(item, quantityToRemove);
            inventory.increaseItemQuantity(item, quantityToRemove);
        }
    }
}

