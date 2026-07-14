package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тесты Differ")
class DifferTest {

    private String getFixturePath(String filename) {
        return getClass().getClassLoader()
                .getResource(filename)
                .getPath();
    }
    private String getExpectedResult(String filename) throws IOException {
        URL resource = getClass().getClassLoader()
                .getResource("expected/" + filename);

        return Files.readString(
                Paths.get(resource.getPath()),
                StandardCharsets.UTF_8
        ).replaceAll("\\r\\n", "\n");
    }

    @Test
    @DisplayName("JSON → stylish (default)")
    void testJsonToStylishDefault() throws Exception {
        String file1 = getFixturePath("file1Test.json");
        String file2 = getFixturePath("file2Test.json");
        String expected = getExpectedResult("json_to_stylish.txt");

        String result = Differ.generate(file1, file2);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("JSON → stylish (explicit)")
    void testJsonToStylishExplicit() throws Exception {
        String file1 = getFixturePath("file1Test.json");
        String file2 = getFixturePath("file2Test.json");
        String expected = getExpectedResult("json_to_stylish.txt");
        String result = Differ.generate(file1, file2, "stylish");

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("JSON → plain")
    void testJsonToPlain() throws Exception {
        String file1 = getFixturePath("file1Test.json");
        String file2 = getFixturePath("file2Test.json");
        String expected = getExpectedResult("json_to_plain.txt");
        String result = Differ.generate(file1, file2, "plain");

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("JSON → json")
    void testJsonToJsonFormat() throws Exception {
        String file1 = getFixturePath("file1Test.json");
        String file2 = getFixturePath("file2Test.json");
        String expected = getExpectedResult("json_to_json.json");
        String result = Differ.generate(file1, file2, "json");

        ObjectMapper mapper = new ObjectMapper();
        Object expectedObj = mapper.readValue(expected, Object.class);
        Object resultObj = mapper.readValue(result, Object.class);

        assertThat(resultObj).isEqualTo(expectedObj);
    }

    // ==================== YAML ВХОДНЫЕ ФАЙЛЫ ====================

    @Test
    @DisplayName("YAML → stylish (default)")
    void testYamlToStylishDefault() throws Exception {
        String file1 = getFixturePath("file1Test.yml");
        String file2 = getFixturePath("file2Test.yml");
        String expected = getExpectedResult("yaml_to_stylish.txt");
        String result = Differ.generate(file1, file2);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Yaml → stylish (explicit)")
    void testYamlToStylishExplicit() throws Exception {
        String file1 = getFixturePath("file1Test.yml");
        String file2 = getFixturePath("file2Test.yml");
        String expected = getExpectedResult("yaml_to_stylish.txt");
        String result = Differ.generate(file1, file2, "stylish");

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("YAML → plain")
    void testYamlToPlain() throws Exception {
        String file1 = getFixturePath("file1Test.yml");
        String file2 = getFixturePath("file2Test.yml");
        String expected = getExpectedResult("yaml_to_plain.txt");
        String result = Differ.generate(file1, file2, "plain");

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("YAML → json")
    void testYamlToJsonFormat() throws Exception {
        String file1 = getFixturePath("file1Test.yml");
        String file2 = getFixturePath("file2Test.yml");
        String expected = getExpectedResult("yaml_to_json.json");
        String result = Differ.generate(file1, file2, "json");

        ObjectMapper mapper = new ObjectMapper();
        Object expectedObj = mapper.readValue(expected, Object.class);
        Object resultObj = mapper.readValue(result, Object.class);

        assertThat(resultObj).isEqualTo(expectedObj);
    }
}
