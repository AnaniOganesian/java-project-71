package hexlet.code.formatters;

import hexlet.code.DiffNode;
import java.util.List;
import java.util.Map;

public class PlainFormatter {

    public static String format(List<DiffNode> diffNodes) {
        StringBuilder result = new StringBuilder();

        for (var node : diffNodes) {
            switch (node.getStatus()) {
                case UNCHANGED:
                    // Не выводим неизменённые ключи
                    break;

                case ADDED:
                    result.append(String.format("Property '%s' was added with value: %s\n",
                            node.getKey(),
                            formatValueForPlain(node.getValue2())));
                    break;

                case REMOVED:
                    result.append(String.format("Property '%s' was removed\n",
                            node.getKey()));
                    break;

                case CHANGED:
                    result.append(String.format("Property '%s' was updated. From %s to %s\n",
                            node.getKey(),
                            formatValueForPlain(node.getValue1()),
                            formatValueForPlain(node.getValue2())));
                    break;

                default:
                    throw new IllegalArgumentException("Unknown status: " + node.getStatus());
            }
        }

        // Убираем последний перевод строки
        if (result.length() > 0) {
            result.setLength(result.length() - 1);
        }

        return result.toString();
    }

    private static String formatValueForPlain(Object value) {
        if (value == null) {
            return "null";
        }

        if (isComplexValue(value)) {
            return "[complex value]";
        }

        if (value instanceof String) {
            return "'" + value + "'";
        }

        if (value instanceof Boolean || value instanceof Number) {
            return value.toString();
        }

        return "[complex value]";
    }

    private static boolean isComplexValue(Object value) {
        if (value == null) {
            return false;
        }
        return value instanceof Map || value instanceof java.util.List;
    }
}
