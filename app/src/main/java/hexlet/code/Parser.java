package hexlet.code;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.util.Map;

public class Parser {

/*    // Парсит JSON или YAML файл
    public static Map<String, Object> parse(String data) throws IOException {
        // Читаем содержимое файла
        String content = Files.readString(Paths.get(data));

        // Проверяем расширение файла
        if (data.endsWith(".json")) {
            return parseJson(content);
        } else if (data.endsWith(".yaml") || data.endsWith(".yml")) {
            return parseYaml(content);
        } else {
            // Если неизвестный формат
            throw new IOException("Unknown file format: " + data);
        }
    }*/

    // Парсит JSON
    public static Map<String, Object> parseJson(String content) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, Map.class);
    }

    // Парсит YAML
    public static Map<String, Object> parseYaml(String content) throws Exception {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(content, Map.class);
    }
}
