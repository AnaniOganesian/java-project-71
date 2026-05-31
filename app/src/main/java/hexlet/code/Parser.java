package hexlet.code;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.IOException;
import java.nio.file.Files;
// import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Parser {

    // Парсит JSON или YAML файл
    public static Map<String, Object> parse(String filePath) throws IOException {
        // Читаем содержимое файла
        String fileContent = Files.readString(Paths.get(filePath));

        // Проверяем расширение файла
        if (filePath.endsWith(".json")) {
            return parseJson(fileContent);
        } else if (filePath.endsWith(".yaml") || filePath.endsWith(".yml")) {
            return parseYaml(fileContent);
        } else {
            // Если неизвестный формат
            throw new IOException("Unknown file format: " + filePath);
        }
    }

    // Парсит JSON
    private static Map<String, Object> parseJson(String content) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, Map.class);
    }

    // Парсит YAML
    private static Map<String, Object> parseYaml(String content) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(content, Map.class);
    }
}
