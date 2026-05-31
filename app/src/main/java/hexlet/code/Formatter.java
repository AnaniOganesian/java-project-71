package hexlet.code;

import java.util.List;

public class Formatter {

    // Форматирует список DiffNode в нужный формат
    public static String format(List<DiffNode> diffNodes) {
        return formatStylish(diffNodes);
    }

    // Форматирует в stylish формат (по умолчанию)
    private static String formatStylish(List<DiffNode> diffNodes) {
        StringBuilder result = new StringBuilder("{\n");

        for (var node : diffNodes) {
            switch (node.getStatus()) {
                case UNCHANGED:
                    result.append(String.format("    %s: %s\n",
                            node.getKey(),
                            formatValue(node.getValue1())));
                    break;

                case ADDED:
                    result.append(String.format("  + %s: %s\n",
                            node.getKey(),
                            formatValue(node.getValue2())));
                    break;

                case REMOVED:
                    result.append(String.format("  - %s: %s\n",
                            node.getKey(),
                            formatValue(node.getValue1())));
                    break;

                case CHANGED:
                    // Сначала старое значение, потом новое
                    result.append(String.format("  - %s: %s\n",
                            node.getKey(),
                            formatValue(node.getValue1())));
                    result.append(String.format("  + %s: %s\n",
                            node.getKey(),
                            formatValue(node.getValue2())));
                    break;
                default:
                    // Требование линтера.. по факту не особо-то и нужен
                    throw new IllegalArgumentException("Unknown status: " + node.getStatus());
            }
        }

        result.append("}");
        return result.toString();
    }

    // Форматирует значение в строку
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
        // Для объектов и массивов просто используем toString()
        return value.toString();
    }
}
