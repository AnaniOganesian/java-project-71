package hexlet.code;

import hexlet.code.CommandLine.Command;
import hexlet.code.CommandLine.Option;
import hexlet.code.CommandLine.Parameters;

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
        // Логика приложения
        System.out.println("Comparing: " + filepath1 + " and " + filepath2);
        System.out.println("Format: " + format);
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);

    }
}