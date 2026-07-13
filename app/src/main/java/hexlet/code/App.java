package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;


@CommandLine.Command(
        name = "gendiff",
        description = "Compares two configuration files and shows a difference.",
        mixinStandardHelpOptions = true,
        version = "gendiff 1.0"
)
public class App implements Runnable {

    @Parameters(
            index = "0",
            paramLabel = "filepath1",
            description = "path to first file",
            arity = "0..1"
    )
    private String filepath1;

    @Parameters(
            index = "1",
            paramLabel = "filepath2",
            description = "path to second file",
            arity = "0..1"
    )
    private String filepath2;

    @Option(
            names = {"-f", "--format"},
            paramLabel = "format",
            description = "output format (stylish, plain, json)",
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

            // Генерируем diff, по умолчанию - Stylish.
            // Если добавить тип форматтера третьим параметром, то будет работать иной тип
            String diff = Differ.generate(filepath1, filepath2, format);

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
