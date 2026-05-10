package hexlet.code;

import hexlet.code.CommandLine.Command;
import hexlet.code.CommandLine.Option;
import hexlet.code.CommandLine.Parameters;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

@Command(
        name = "gendiff",
        description = "Compares two configuration files and shows a difference.",
        mixinStandardHelpOptions = true,
        version = "gendiff 1.0"
)
public class App implements Runnable {

    @Parameters(
            index = "0",
            description = "path to first file",
            arity = "0..1"
    )
    private String filepath1;

    @Parameters(
            index = "1",
            description = "path to second file",
            arity = "0..1"
    )
    private String filepath2;

    @Option(
            names = {"-f", "--format"},
            description = "output format (json, plain, stylish)",
            defaultValue = "stylish"
    )
    private String format;

    @Override
    public void run() {
        try {
            // Читаем и парсим оба файла
            Map<String, Object> config1 = readAndParseJson(filepath1);
            Map<String, Object> config2 = readAndParseJson(filepath2);

            System.out.println("File 1: " + config1);
            System.out.println("File 2: " + config2);
            System.out.println("Format: " + format);

            // Здесь будет логика сравнения файлов

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // Читает файл и парсит JSON содержимое
    private Map<String, Object> readAndParseJson(String filePath) throws Exception {
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        return parse(content);
    }

    // Парсит JSON строку в Map
    private Map<String, Object> parse(String content) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, Map.class);
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);

    }
}