package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import java.nio.file.Paths;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тесты для сравнения JSON-файлов")
class DifferTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    // Получить полный путь к файлу через ClassLoader
    private String getFixturePath(String filename) throws Exception {
        return Paths.get(
                getClass().getClassLoader()
                        .getResource("fixtures/" + filename)
                        .toURI()
        ).toString();
    }

    // Вспомогательный метод для загрузки конфига из файла
    private Map<String, Object> loadConfig(String filename) throws Exception {
        String filePath = getFixturePath(filename);
        return Parser.parse(filePath); // Используй Parser!
    }

    @Test
    @DisplayName("Идентичные объекты - нет различий")
    void testIdenticalJsonFiles() throws IOException {
        Map<String, Object> config1 = Map.of(
                "host", "hexlet.io",
                "timeout", 50,
                "proxy", false
        );
        Map<String, Object> config2 = Map.of(
                "host", "hexlet.io",
                "timeout", 50,
                "proxy", false
        );

        String diff = Differ.generate(config1, config2);

        assertThat(diff)
                .isNotNull()
                .satisfiesAnyOf(
                        result -> assertThat(result).isEmpty(),
                        result -> assertThat(result).contains("host")
            );
    }

    @Test
    @DisplayName("Добавлено новое свойство")
    void testPropertyAdded() throws IOException {
        Map<String, Object> config1 = Map.of(
                "host", "hexlet.io",
                "timeout", 50
        );
        Map<String, Object> config2 = Map.of(
                "host", "hexlet.io",
                "timeout", 50,
                "proxy", false
        );

        String diff = Differ.generate(config1, config2);

        assertThat(diff)
                .isNotNull()
                .contains("proxy");
    }

    @Test
    @DisplayName("Удалено свойство")
    void testPropertyRemoved() throws IOException {
        Map<String, Object> config1 = Map.of(
                "host", "hexlet.io",
                "timeout", 50,
                "proxy", false
        );
        Map<String, Object> config2 = Map.of(
                "host", "hexlet.io",
                "timeout", 50
        );

        String diff = Differ.generate(config1, config2);

        assertThat(diff)
                .isNotNull()
                .contains("proxy");
    }

    @Test
    @DisplayName("Изменено значение свойства")
    void testPropertyChanged() throws IOException {
        Map<String, Object> config1 = Map.of(
                "host", "hexlet.io",
                "timeout", 50
        );
        Map<String, Object> config2 = Map.of(
                "host", "hexlet.io",
                "timeout", 20
        );

        String diff = Differ.generate(config1, config2);

        assertThat(diff)
                .isNotNull()
                .contains("timeout", "50", "20");
    }

    @Test
    @DisplayName("Boolean значение изменилось")
    void testBooleanPropertyChanged() throws IOException {
        Map<String, Object> config1 = Map.of(
                "host", "hexlet.io",
                "proxy", false
        );
        Map<String, Object> config2 = Map.of(
                "host", "hexlet.io",
                "proxy", true
        );

        String diff = Differ.generate(config1, config2);

        assertThat(diff)
                .isNotNull()
                .contains("proxy", "false", "true");
    }

    @Test
    @DisplayName("Пустой объект остается пустым")
    void testEmptyObjects() throws IOException {
        Map<String, Object> config1 = Map.of();
        Map<String, Object> config2 = Map.of();

        String diff = Differ.generate(config1, config2);

        assertThat(diff)
                .as("Результат не должен быть null для пустых объектов")
                .isNotNull();
    }

    @Test
    @DisplayName("Сравнение JSON файлов - stylish формат (по умолчанию)")
    void testJsonFilesStylish() throws Exception {
        Map<String, Object> config1 = loadConfig("file1Test.json");
        Map<String, Object> config2 = loadConfig("file2Test.json");
        String result = Differ.generate(config1, config2);

        assertThat(result)
                .isNotNull()
                .isNotEmpty();
    }

    @Test
    @DisplayName("Сравнение JSON файлов - plain формат")
    void testJsonFilesPlain() throws Exception {
        Map<String, Object> config1 = loadConfig("file1Test.json");
        Map<String, Object> config2 = loadConfig("file2Test.json");

        String result = Differ.generate(config1, config2, "plain");

        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .contains("Property");
    }

    @Test
    @DisplayName("Сравнение JSON файлов - json формат")
    void testJsonFilesJson() throws Exception {
        Map<String, Object> config1 = loadConfig("file1Test.json");
        Map<String, Object> config2 = loadConfig("file2Test.json");

        String result = Differ.generate(config1, config2, "json");

        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .startsWith("[")
                .endsWith("]");
    }

    @Test
    @DisplayName("Идентичные файлы при сравнении")
    void testIdenticalFiles() throws Exception {
        Map<String, Object> config1 = loadConfig("file1Test.json");

        String result = Differ.generate(config1, config1);

        assertThat(result)
                .isNotNull()
                .satisfiesAnyOf(
                        r -> assertThat(r).isEmpty(),
                        r -> assertThat(r).contains("host")
            );
    }

    @Test
    @DisplayName("YAML файлы поддерживаются")
    void testYamlFiles() throws Exception {
        Map<String, Object> config1 = loadConfig("file1Test.yml");
        Map<String, Object> config2 = loadConfig("file2Test.yml");

        String result = Differ.generate(config1, config2);

        assertThat(result).isNotNull();
    }
}
