package hexlet.code;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тесты Differ")
class DifferTest {

    private String getFixturePath(String filename) {
        return getClass().getClassLoader()
                .getResource("fixtures/" + filename)
                .getPath();
    }

    @Test
    @DisplayName("Сравнение JSON файлов - stylish по умолчанию")
    void testJsonStylishDefault() throws Exception {
        String file1 = getFixturePath("file1Test.json");
        String file2 = getFixturePath("file2Test.json");

        String result = Differ.generate(file1, file2);

        assertThat(result)
                .isNotNull()
                .isNotEmpty();
    }

    @Test
    @DisplayName("Сравнение JSON файлов - stylish явно")
    void testJsonStylishExplicit() throws Exception {
        String file1 = getFixturePath("file1Test.json");
        String file2 = getFixturePath("file2Test.json");

        String result = Differ.generate(file1, file2, "stylish");

        assertThat(result)
                .isNotNull()
                .isNotEmpty();
    }

    @Test
    @DisplayName("Сравнение JSON файлов - plain формат")
    void testJsonPlain() throws Exception {
        String file1 = getFixturePath("file1Test.json");
        String file2 = getFixturePath("file2Test.json");

        String result = Differ.generate(file1, file2, "plain");

        assertThat(result)
                .isNotNull()
                .isNotEmpty();
    }

    @Test
    @DisplayName("Сравнение JSON файлов - json формат")
    void testJsonJsonFormat() throws Exception {
        String file1 = getFixturePath("file1Test.json");
        String file2 = getFixturePath("file2Test.json");

        String result = Differ.generate(file1, file2, "json");

        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .startsWith("[")
                .endsWith("]");
    }

    @Test
    @DisplayName("Сравнение YAML файлов - stylish по умолчанию")
    void testYamlStylishDefault() throws Exception {
        String file1 = getFixturePath("file1Test.yml");
        String file2 = getFixturePath("file2Test.yml");

        String result = Differ.generate(file1, file2);

        assertThat(result)
                .isNotNull()
                .isNotEmpty();
    }

    @Test
    @DisplayName("Сравнение YAML файлов - plain формат")
    void testYamlPlain() throws Exception {
        String file1 = getFixturePath("file1Test.yml");
        String file2 = getFixturePath("file2Test.yml");

        String result = Differ.generate(file1, file2, "plain");

        assertThat(result)
                .isNotNull()
                .isNotEmpty();
    }

    @Test
    @DisplayName("Сравнение YAML файлов - json формат")
    void testYamlJsonFormat() throws Exception {
        String file1 = getFixturePath("file1Test.yml");
        String file2 = getFixturePath("file2Test.yml");

        String result = Differ.generate(file1, file2, "json");

        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .startsWith("[")
                .endsWith("]");
    }
}
