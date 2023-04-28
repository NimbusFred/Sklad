package util;

// Importy potřebných knihoven a tříd
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Item;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Serializer {

    private final String jsonFilePath;
    private final String csvFilePath;

    // Konstruktor třídy Serializer
    public Serializer() {
        // Nastavení cest k souborům pro JSON a CSV
        this.jsonFilePath = "src/main/java/data/items.json";
        this.csvFilePath = "src/main/java/data/items.csv";

        // Kontrola, zda soubory existují, a pokud ne, vytvoří je
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

    // Metoda pro serializaci seznamu objektů Item do souboru JSON
    public void serializeToJson(List<Item> items) throws IOException {
        // Vytvoření ObjectMapper pro serializaci seznamu objektů
        ObjectMapper objectMapper = new ObjectMapper();
        // Serializace seznamu objektů do řetězce JSON
        String content = objectMapper.writeValueAsString(items);
        // Uložení řetězce JSON do souboru
        Path path = Paths.get(jsonFilePath);
        Files.writeString(path, content, StandardCharsets.UTF_8);
    }

    // Metoda pro serializaci seznamu objektů Item do souboru CSV
    public void serializeToCsv(List<Item> items) throws IOException {
        // Vytvoření StringBuilder pro ukládání CSV řádků
        StringBuilder sb = new StringBuilder();
        // Iterujte přes seznam objektů Item a vytvořte CSV řádky
        for (Item item : items) {
            sb.append(item.getName());
            sb.append(",");
            sb.append(item.getPrice());
            sb.append(",");
            sb.append(item.getQuantity());
            sb.append(",");
            sb.append(item.getCategory());
            sb.append("\n");
        }
        // Převedení StringBuilder na řetězec
        String content = sb.toString();
        // Uložení řetězce CSV do souboru
        Path path = Paths.get(csvFilePath);
        Files.writeString(path, content, StandardCharsets.UTF_8);
    }
}
