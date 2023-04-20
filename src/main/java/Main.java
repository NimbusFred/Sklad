import model.Inventory;
import model.Item;
import util.Deserializer;
import util.Serializer;
import view.MainFrame;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Zkontrolujte, zda složka "data" existuje, a pokud ne, vytvořte ji
        Path dataFolderPath = Paths.get("data");
        if (!Files.exists(dataFolderPath)) {
            try {
                Files.createDirectories(dataFolderPath);
            } catch (IOException e) {
                System.err.println("Nepodařilo se vytvořit složku 'data': " + e.getMessage());
                return;
            }
        }

        // Načtení položek ze souboru
        String filePath = "data/items.json"; // Cesta k souboru, odkud načíst data
        Serializer serializer = new Serializer(filePath);
        List<Item> items;

        // Načtení položek ze souboru
        Deserializer deserializer = new Deserializer(filePath);
        List<Item> itemsFromFile = new ArrayList<>();
        try {
            itemsFromFile = deserializer.deserializeFromJson();
        } catch (IOException e) {
            System.err.println("Nepodařilo se načíst data ze souboru: " + e.getMessage());
        }

        // Přidání položek do Inventory
        Inventory inventory = new Inventory();
        for (Item item : itemsFromFile) {
            inventory.addItem(item);
            System.out.println(item);
        }

        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame(inventory); // Předání načtených položek do MainFrame
            mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    int confirmExit = JOptionPane.showConfirmDialog(
                            mainFrame,
                            "Máte neuložené změny, opravdu chcete ukončit aplikaci?",
                            "Ukončit",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );
                    if (confirmExit == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
            });
            mainFrame.setVisible(true);
        });
    }
}
