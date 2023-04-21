package util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Inventory;
import model.Item;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Deserializer {

    private String filePath;

    public Deserializer() {
        this.filePath = "src/main/java/data/items.json";
        try {
            Path path = Paths.get(filePath);
            if (Files.notExists(path)) {
                Files.createFile(path);
            }
        } catch (IOException e) {
            System.out.println("Chyba při připojování k souboru: " + e.getMessage());
        }
    }

    public void loadItemsToInventory(Inventory inventory) {
        ArrayList<Item> itemsFromFile = new ArrayList<>();
        try {
            itemsFromFile = deserializeFromJson();
        } catch (IOException e) {
            System.err.println("Nepodařilo se načíst data ze souboru: " + e.getMessage());
        }

        inventory.addItemsInOrder(itemsFromFile);
    }

    public ArrayList<Item> deserializeFromJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Path path = Paths.get(filePath);
        String content = Files.readString(path, StandardCharsets.UTF_8);
        return objectMapper.readValue(content, new TypeReference<ArrayList<Item>>() {});
    }

}
