package hexlet.code.formatters;

import hexlet.code.DiffNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonFormatter {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String format(List<DiffNode> diffNodes) throws Exception {
        List<Map<String, Object>> result = new java.util.ArrayList<>();

        for (var node : diffNodes) {
            Map<String, Object> nodeMap = new LinkedHashMap<>();
            nodeMap.put("key", node.getKey());
            nodeMap.put("status", node.getStatus().toString().toLowerCase());

            if (node.getValue1() != null) {
                nodeMap.put("oldValue", node.getValue1());
            }
            if (node.getValue2() != null) {
                nodeMap.put("newValue", node.getValue2());
            }

            result.add(nodeMap);
        }

        return MAPPER.writeValueAsString(result);
    }
}
