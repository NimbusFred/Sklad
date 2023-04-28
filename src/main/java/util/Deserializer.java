package util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Item;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Deserializer {

    private final String jsonFilePath;
    private final String csvFilePath;


    public Deserializer() {
        this.jsonFilePath = "src/main/java/data/items.json";
        this.csvFilePath = "src/main/java/data/items.csv";
        try {
            Path jsonPath = Paths.get(jsonFilePath);
            if (Files.notExists(jsonPath)) {
                Files.createFile(jsonPath);
            }
            Path csvPath = Paths.get(csvFilePath);
            if (Files.notExists(csvPath)) {
                Files.createFile(csvPath);
            }
        } catch (IOException e) {
            System.out.println("Chyba při připojování k souboru: " + e.getMessage());
        }
    }

    public ArrayList<Item> deserializeFromJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Path path = Paths.get(jsonFilePath);
        String content = Files.readString(path, StandardCharsets.UTF_8);
        return objectMapper.readValue(content, new TypeReference<>() {
        });
    }


    public ArrayList<Item> deserializeFromCSV() throws IOException {
        String csvFilePath = "src/main/java/data/items.csv";
        Path path = Paths.get(csvFilePath);
        if (Files.notExists(path)) {
            throw new IOException("Soubor items.csv nebyl nalezen");
        }

        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        ArrayList<Item> items = new ArrayList<>();

        for (String line : lines) {
            String[] fields = line.split(",");
            if (fields.length >= 4) {
                String name = fields[0].trim();
                double price = Double.parseDouble(fields[1].trim());
                int quantity = Integer.parseInt(fields[2].trim());
                String category = fields[3].trim();

                Item item = new Item(name, price, quantity, category);
                items.add(item);
            }
        }
        System.out.println(items);
        return items;
    }

}
