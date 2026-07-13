package hexlet.code;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.util.Map;

public class Parser {

    public static Map<String, Object> parse(String data, String formate) throws IOException {
        if (formate.equals("json")) {
            return parseJson(data);
        } else if (formate.equals("yml") || formate.equals("yaml")) {
            return parseYaml(data);
        } else {
            throw new IOException("Unsupported format: " + formate);
        }
    }

    // Парсит JSON
    public static Map<String, Object> parseJson(String content) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, Map.class);
    }

    // Парсит YAML
    public static Map<String, Object> parseYaml(String content) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(content, Map.class);
    }
}
