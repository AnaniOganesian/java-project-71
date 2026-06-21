package hexlet.code;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тесты Differ")
class DifferTest {

    private String getFixturePath(String filename) {
        return getClass().getClassLoader()
                .getResource(filename)
                .getPath();
    }

    @Test
    @DisplayName("JSON → stylish (default)")
    void testJsonToStylishDefault() throws Exception {
        String file1 = getFixturePath("file1Test.json");
        String file2 = getFixturePath("file2Test.json");

        // Без указания формата - должен использоваться stylish
        String result = Differ.generate(file1, file2);

        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .contains("  ")  // stylish использует пробелы
                .doesNotStartWith("[");  // не JSON формат
    }

    @Test
    @DisplayName("JSON → stylish (explicit)")
    void testJsonToStylishExplicit() throws Exception {
        String file1 = getFixturePath("file1Test.json");
        String file2 = getFixturePath("file2Test.json");

        String result = Differ.generate(file1, file2, "stylish");

        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .contains("  ")  // stylish использует пробелы
                .doesNotStartWith("[");
    }

    @Test
    @DisplayName("JSON → plain")
    void testJsonToPlain() throws Exception {
        String file1 = getFixturePath("file1Test.json");
        String file2 = getFixturePath("file2Test.json");

        String result = Differ.generate(file1, file2, "plain");

        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .doesNotStartWith("[")  // не JSON
                .doesNotContain("  ");  // plain не использует отступы
    }

    @Test
    @DisplayName("JSON → json")
    void testJsonToJsonFormat() throws Exception {
        String file1 = getFixturePath("file1Test.json");
        String file2 = getFixturePath("file2Test.json");

        String result = Differ.generate(file1, file2, "json");

        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .startsWith("[")
                .endsWith("]")
                .contains("\"key\"");  // JSON формат
    }

    // ==================== YAML ВХОДНЫЕ ФАЙЛЫ ====================

    @Test
    @DisplayName("YAML → stylish (default)")
    void testYamlToStylishDefault() throws Exception {
        String file1 = getFixturePath("file1Test.yml");
        String file2 = getFixturePath("file2Test.yml");

        // Без указания формата - должен использоваться stylish
        String result = Differ.generate(file1, file2);

        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .contains("  ")  // stylish использует пробелы
                .doesNotStartWith("[");  // не JSON формат
    }

    @Test
    @DisplayName("YAML → plain")
    void testYamlToPlain() throws Exception {
        String file1 = getFixturePath("file1Test.yml");
        String file2 = getFixturePath("file2Test.yml");

        String result = Differ.generate(file1, file2, "plain");

        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .doesNotStartWith("[")  // не JSON
                .doesNotContain("  ");  // plain не использует отступы
    }

    @Test
    @DisplayName("YAML → json")
    void testYamlToJsonFormat() throws Exception {
        String file1 = getFixturePath("file1Test.yml");
        String file2 = getFixturePath("file2Test.yml");

        String result = Differ.generate(file1, file2, "json");

        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .startsWith("[")
                .endsWith("]")
                .contains("\"key\"");  // JSON формат
    }
}
