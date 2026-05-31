package hexlet.code;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class JsonFormatterTest {

    @Test
    void testJsonFormatContainsKey() {
        Map<String, Object> config1 = new HashMap<>();
        config1.put("key1", "value1");

        Map<String, Object> config2 = new HashMap<>();
        config2.put("key1", "value2");

        String result = Differ.generate(config1, config2, "json");

        assertTrue(result.contains("\"key\""));
        assertTrue(result.contains("\"key1\""));
        assertTrue(result.contains("\"status\""));
        assertTrue(result.contains("changed"));
    }

    @Test
    void testJsonFormatRemoved() {
        Map<String, Object> config1 = new HashMap<>();
        config1.put("key1", "value1");

        Map<String, Object> config2 = new HashMap<>();

        String result = Differ.generate(config1, config2, "json");

        assertTrue(result.contains("\"status\""));
        assertTrue(result.contains("removed"));
        assertTrue(result.contains("oldValue"));
    }

    @Test
    void testJsonFormatAdded() {
        Map<String, Object> config1 = new HashMap<>();
        Map<String, Object> config2 = new HashMap<>();
        config2.put("key2", "value2");

        String result = Differ.generate(config1, config2, "json");

        assertTrue(result.contains("\"status\""));
        assertTrue(result.contains("added"));
        assertTrue(result.contains("newValue"));
    }

    @Test
    void testJsonFormatValidJson() {
        Map<String, Object> config1 = new HashMap<>();
        config1.put("a", 1);

        Map<String, Object> config2 = new HashMap<>();
        config2.put("a", 2);

        String result = Differ.generate(config1, config2, "json");

        // JSON должен начинаться с [ и заканчиваться на ]
        assertTrue(result.trim().startsWith("["));
        assertTrue(result.trim().endsWith("]"));
    }
}
