package hexlet.code;

import hexlet.code.formatters.StylishFormatter;
import hexlet.code.formatters.PlainFormatter;
import java.util.List;

public class Formatter {

    public static String format(List<DiffNode> diffNodes, String formatType) {
        switch (formatType) {
            case "plain":
                return PlainFormatter.format(diffNodes);
            case "stylish":
            default:
                return StylishFormatter.format(diffNodes);
        }
    }
}
