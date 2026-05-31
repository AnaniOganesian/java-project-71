package hexlet.code;
import java.util.Map;
import java.util.Set;
import java.util.Objects;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;

public class Differ {

    public static String generate(Map<String, Object> config1, Map<String, Object> config2, String format) {
        List<DiffNode> diffNodes = buildDiff(config1, config2);
        return Formatter.format(diffNodes, format);
    }

    public static String generate(Map<String, Object> config1, Map<String, Object> config2) {
        return generate(config1, config2, "stylish");
    }

    private static List<DiffNode> buildDiff(Map<String, Object> config1, Map<String, Object> config2) {

        List<DiffNode> result = new ArrayList<>();

        Set<String> allKeys = new TreeSet<>();
        allKeys.addAll(config1.keySet());
        allKeys.addAll(config2.keySet());

        for (var key : allKeys) {
            var value1 = config1.get(key);
            var value2 = config2.get(key);

            if (!config1.containsKey(key)) {
                // Ключ только во втором файле
                result.add(new DiffNode(key, null, value2, DiffNode.Status.ADDED));
            } else if (!config2.containsKey(key)) {
                // Ключ только в первом файле
                result.add(new DiffNode(key, value1, null, DiffNode.Status.REMOVED));
            } else if (Objects.equals(value1, value2)) {
                // Значения одинаковые
                result.add(new DiffNode(key, value1, value2, DiffNode.Status.UNCHANGED));
            } else {
                // Значения разные
                result.add(new DiffNode(key, value1, value2, DiffNode.Status.CHANGED));
            }
        }

        return result;

    }

    private static String formatValue(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof String) {
            return value.toString();
        }
        if (value instanceof Boolean || value instanceof Number) {
            return value.toString();
        }
        return value.toString();
    }

}
