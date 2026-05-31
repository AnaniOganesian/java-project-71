package hexlet.code;

import hexlet.code.CommandLine.Command;
import hexlet.code.CommandLine.Option;
import hexlet.code.CommandLine.Parameters;
import java.util.Map;

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
            // Проверяем что оба файла передали
            if (filepath1 == null || filepath2 == null) {
                System.out.println("Error: Please provide two file paths");
                return;
            }

            // Используем Parser для чтения файлов
            Map<String, Object> config1 = Parser.parse(filepath1);
            Map<String, Object> config2 = Parser.parse(filepath2);

            // Генерируем diff
            String diff = Differ.generate(config1, config2);

            // Выводим результат
            System.out.println(diff);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
