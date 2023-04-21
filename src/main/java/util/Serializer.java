package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Item;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Serializer {

    private String filePath;

    public Serializer() {
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

    public void serializeToJson(List<Item> items) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(items);
        Path path = Paths.get(filePath);
        Files.writeString(path, content, StandardCharsets.UTF_8);
    }

    public void serializeToCsv(List<Item> items) throws IOException {
        StringBuilder sb = new StringBuilder();
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
        String content = sb.toString();
        Path path = Paths.get(filePath);

        Files.writeString(path, content, StandardCharsets.UTF_8);
    }
}
