package hexlet.code.formatters;

import hexlet.code.DiffNode;
import java.util.List;

public class StylishFormatter {

    public static String format(List<DiffNode> diffNodes) {
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
                    result.append(String.format("  - %s: %s\n",
                            node.getKey(),
                            formatValue(node.getValue1())));
                    result.append(String.format("  + %s: %s\n",
                            node.getKey(),
                            formatValue(node.getValue2())));
                    break;

                default:
                    throw new IllegalArgumentException("Unknown status: " + node.getStatus());
            }
        }

        result.append("}");
        return result.toString();
    }

    private static String formatValue(Object value) {
        if (value == null) {
            return "null";
        }
        return value.toString();
    }
}
