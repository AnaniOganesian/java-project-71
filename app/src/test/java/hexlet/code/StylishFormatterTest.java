package hexlet.code;

import hexlet.code.DiffNode.Status;
import hexlet.code.formatters.StylishFormatter;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class StylishFormatterTest {

    @Test
    void testFormatEmptyList() {
        List<DiffNode> diffNodes = List.of();

        String result = StylishFormatter.format(diffNodes);

        assertThat(result).isEqualTo("{\n}");
    }

    @Test
    void testFormatUnchangedProperty() {
        DiffNode node = new DiffNode("key1", "value1", "value1", Status.UNCHANGED);
        List<DiffNode> diffNodes = List.of(node);

        String result = StylishFormatter.format(diffNodes);

        assertThat(result).contains("    key1: value1");
    }

    @Test
    void testFormatAddedProperty() {
        DiffNode node = new DiffNode("key1", null, "value1", Status.ADDED);
        List<DiffNode> diffNodes = List.of(node);

        String result = StylishFormatter.format(diffNodes);

        assertThat(result).contains("  + key1: value1");
    }

    @Test
    void testFormatRemovedProperty() {
        DiffNode node = new DiffNode("key1", "value1", null, Status.REMOVED);
        List<DiffNode> diffNodes = List.of(node);

        String result = StylishFormatter.format(diffNodes);

        assertThat(result).contains("  - key1: value1");
    }

    @Test
    void testFormatChangedProperty() {
        DiffNode node = new DiffNode("key1", "old", "new", Status.CHANGED);
        List<DiffNode> diffNodes = List.of(node);

        String result = StylishFormatter.format(diffNodes);

        assertThat(result)
                .contains("  - key1: old")
                .contains("  + key1: new");
    }

    @Test
    void testFormatNullValue() {
        DiffNode node = new DiffNode("key1", null, null, Status.UNCHANGED);
        List<DiffNode> diffNodes = List.of(node);

        String result = StylishFormatter.format(diffNodes);

        assertThat(result).contains("key1: null");
    }

    @Test
    void testFormatBooleanValue() {
        DiffNode node = new DiffNode("flag", true, false, Status.CHANGED);
        List<DiffNode> diffNodes = List.of(node);

        String result = StylishFormatter.format(diffNodes);

        assertThat(result)
                .contains("flag: true")
                .contains("flag: false");
    }

    @Test
    void testFormatNumberValue() {
        DiffNode node = new DiffNode("count", 42, 100, Status.CHANGED);
        List<DiffNode> diffNodes = List.of(node);

        String result = StylishFormatter.format(diffNodes);

        assertThat(result)
                .contains("count: 42")
                .contains("count: 100");
    }

    @Test
    void testFormatMultipleProperties() {
        DiffNode node1 = new DiffNode("key1", "value1", "value1", Status.UNCHANGED);
        DiffNode node2 = new DiffNode("key2", null, "added", Status.ADDED);
        DiffNode node3 = new DiffNode("key3", "removed", null, Status.REMOVED);
        DiffNode node4 = new DiffNode("key4", "old", "new", Status.CHANGED);
        List<DiffNode> diffNodes = List.of(node1, node2, node3, node4);

        String result = StylishFormatter.format(diffNodes);

        assertThat(result)
                .contains("    key1: value1")
                .contains("  + key2: added")
                .contains("  - key3: removed")
                .contains("  - key4: old")
                .contains("  + key4: new");
    }

    @Test
    void testFormatStartsWithOpenBrace() {
        DiffNode node = new DiffNode("key1", "value1", "value1", Status.UNCHANGED);
        List<DiffNode> diffNodes = List.of(node);

        String result = StylishFormatter.format(diffNodes);

        assertThat(result).startsWith("{\n");
    }

    @Test
    void testFormatEndsWithCloseBrace() {
        DiffNode node = new DiffNode("key1", "value1", "value1", Status.UNCHANGED);
        List<DiffNode> diffNodes = List.of(node);

        String result = StylishFormatter.format(diffNodes);

        assertThat(result).endsWith("}");
    }

    @Test
    void testFormatRemovedHasSignAndTwoSpaces() {
        DiffNode node = new DiffNode("key1", "value1", null, Status.REMOVED);
        List<DiffNode> diffNodes = List.of(node);

        String result = StylishFormatter.format(diffNodes);

        assertThat(result).contains("  - key1");
    }

    @Test
    void testFormatStructure() {
        DiffNode node1 = new DiffNode("a", "1", "1", Status.UNCHANGED);
        DiffNode node2 = new DiffNode("b", "2", "3", Status.CHANGED);
        List<DiffNode> diffNodes = List.of(node1, node2);

        String result = StylishFormatter.format(diffNodes);

        String expected = "{\n    a: 1\n  - b: 2\n  + b: 3\n}";
        assertThat(result).isEqualTo(expected);
    }
}
