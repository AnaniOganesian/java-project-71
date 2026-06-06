package hexlet.code;

import hexlet.code.DiffNode.Status;
import hexlet.code.formatters.PlainFormatter;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

class PlainFormatterTest {

    @Test
    void testFormatAddedProperty() {
        DiffNode node = new DiffNode("key1", null, "value1", Status.ADDED);
        List<DiffNode> diffNodes = List.of(node);

        String result = PlainFormatter.format(diffNodes);

        assertThat(result).contains("Property 'key1' was added with value: 'value1'");
    }

    @Test
    void testFormatRemovedProperty() {
        DiffNode node = new DiffNode("key1", "value1", null, Status.REMOVED);
        List<DiffNode> diffNodes = List.of(node);

        String result = PlainFormatter.format(diffNodes);

        assertThat(result).contains("Property 'key1' was removed");
    }

    @Test
    void testFormatChangedProperty() {
        DiffNode node = new DiffNode("key1", "old", "new", Status.CHANGED);
        List<DiffNode> diffNodes = List.of(node);

        String result = PlainFormatter.format(diffNodes);

        assertThat(result).contains("Property 'key1' was updated. From 'old' to 'new'");
    }

    @Test
    void testFormatUnchangedPropertyIsSkipped() {
        DiffNode node = new DiffNode("key1", "value1", "value1", Status.UNCHANGED);
        List<DiffNode> diffNodes = List.of(node);

        String result = PlainFormatter.format(diffNodes);

        assertThat(result).isEmpty();
    }

    @Test
    void testFormatNullValue() {
        DiffNode node = new DiffNode("key1", null, null, Status.ADDED);
        List<DiffNode> diffNodes = List.of(node);

        String result = PlainFormatter.format(diffNodes);

        assertThat(result).contains("null");
    }

    @Test
    void testFormatBooleanValue() {
        DiffNode node = new DiffNode("key1", false, true, Status.CHANGED);
        List<DiffNode> diffNodes = List.of(node);

        String result = PlainFormatter.format(diffNodes);

        assertThat(result).contains("false").contains("true");
    }

    @Test
    void testFormatNumberValue() {
        DiffNode node = new DiffNode("key1", 42, 100, Status.CHANGED);
        List<DiffNode> diffNodes = List.of(node);

        String result = PlainFormatter.format(diffNodes);

        assertThat(result).contains("42").contains("100");
    }

    @Test
    void testFormatComplexValueAsMap() {
        Map<String, Object> complexValue = Map.of("nested", "value");
        DiffNode node = new DiffNode("key1", null, complexValue, Status.ADDED);
        List<DiffNode> diffNodes = List.of(node);

        String result = PlainFormatter.format(diffNodes);

        assertThat(result).contains("[complex value]");
    }

    @Test
    void testFormatComplexValueAsList() {
        List<Object> complexValue = List.of(1, 2, 3);
        DiffNode node = new DiffNode("key1", null, complexValue, Status.ADDED);
        List<DiffNode> diffNodes = List.of(node);

        String result = PlainFormatter.format(diffNodes);

        assertThat(result).contains("[complex value]");
    }

    @Test
    void testFormatMultipleProperties() {
        DiffNode node1 = new DiffNode("key1", null, "value1", Status.ADDED);
        DiffNode node2 = new DiffNode("key2", "value2", null, Status.REMOVED);
        DiffNode node3 = new DiffNode("key3", "old", "new", Status.CHANGED);
        List<DiffNode> diffNodes = List.of(node1, node2, node3);

        String result = PlainFormatter.format(diffNodes);

        assertThat(result)
                .contains("Property 'key1' was added with value: 'value1'")
                .contains("Property 'key2' was removed")
                .contains("Property 'key3' was updated. From 'old' to 'new'");
    }

    @Test
    void testFormatEmptyList() {
        List<DiffNode> diffNodes = List.of();

        String result = PlainFormatter.format(diffNodes);

        assertThat(result).isEmpty();
    }

    @Test
    void testFormatStringWithQuotes() {
        DiffNode node = new DiffNode("key1", null, "hello world", Status.ADDED);
        List<DiffNode> diffNodes = List.of(node);

        String result = PlainFormatter.format(diffNodes);

        assertThat(result).contains("'hello world'");
    }

    @Test
    void testFormatOnlyUnchangedProperties() {
        DiffNode node1 = new DiffNode("key1", "value1", "value1", Status.UNCHANGED);
        DiffNode node2 = new DiffNode("key2", "value2", "value2", Status.UNCHANGED);
        List<DiffNode> diffNodes = List.of(node1, node2);

        String result = PlainFormatter.format(diffNodes);

        assertThat(result).isEmpty();
    }

}
