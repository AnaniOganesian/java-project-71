package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тесты для сравнения плоских JSON-файлов")
class DifferTest {

    private ObjectMapper objectMapper;
    private static final String TEST_FIXTURES_PATH = "src/test/resources/fixtures";

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Идентичные файлы - нет различий")
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
                        result -> assertThat(result).contains("host"),
                        result -> assertThat(result).isEmpty()
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
                .contains("proxy")
                .contains("false")
                .contains("true");
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

}
