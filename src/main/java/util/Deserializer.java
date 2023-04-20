package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import model.Item;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Deserializer {

    public List<Item> deserializeFromJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Item.class);
        Path path = Paths.get(filePath);

        String content = Files.readString(path, StandardCharsets.UTF_8);
        return objectMapper.readValue(content, listType);
    }

    public List<Item> deserializeFromCsv(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

        List<Item> items = new ArrayList<>();
        for (String line : lines) {
            String[] fields = line.split(",");
            if (fields.length == 4) {
                String name = fields[0];
                double price = Double.parseDouble(fields[1]);
                int quantity = Integer.parseInt(fields[2]);
                String category = fields[3];

                items.add(new Item(name, price, quantity, category));
            }
        }
        return items;
    }
}

