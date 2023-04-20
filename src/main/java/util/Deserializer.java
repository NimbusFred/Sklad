package util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Item;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Deserializer {

    private String filePath;

    public Deserializer(String filePath) {
        this.filePath = filePath;
    }

    public List<Item> deserializeFromJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Path path = Paths.get(filePath);
        String content = Files.readString(path, StandardCharsets.UTF_8);
        return objectMapper.readValue(content, new TypeReference<List<Item>>() {});
    }
}
