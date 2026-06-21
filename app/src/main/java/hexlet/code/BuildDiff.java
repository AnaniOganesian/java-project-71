package hexlet.code;


import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Objects;

public class BuildDiff {

    public static List<DiffNode> buildDiff(Map<String, Object> config1, Map<String, Object> config2) {

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

}
