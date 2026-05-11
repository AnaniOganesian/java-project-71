package hexlet.code;
import java.util.*;

public class Differ {

    public static String generate(Map<String, Object> config1, Map<String, Object> config2) {
        Set<String> allKeys = new TreeSet<>();
        allKeys.addAll(config1.keySet());
        allKeys.addAll(config2.keySet());

        StringBuilder result = new StringBuilder("{\n");

        for (var key : allKeys) {
            var value1 = config1.get(key);
            var value2 = config2.get(key);

            // Проверяем, если нет в 1, но есть во 2 - ставим плюсик
            if (!config1.containsKey(key) && config2.containsKey(key)) {
                result.append(String.format("  + %s: %s\n", key, formatValue(value2)));
            }
            // Проверяем, если нет в 2, но есть во 1 - ставим минус
            else if (config1.containsKey(key) && !config2.containsKey(key)) {
                result.append(String.format("  - %s: %s\n", key, formatValue(value1)));
            }
            // Проверяем равные данные, показываем без знаков
            else if (Objects.equals(value1, value2)) {
                result.append(String.format("    %s: %s\n", key, formatValue(value1)));
            }
            // Если значение изменилось, то показываем оба варианта.
            else {
                result.append(String.format("  - %s: %s\n", key, formatValue(value1)));
                result.append(String.format("  + %s: %s\n", key, formatValue(value2)));
            }
        }

        result.append("}");
        return result.toString();

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