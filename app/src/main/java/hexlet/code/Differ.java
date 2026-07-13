package hexlet.code;

import static hexlet.code.BuildDiff.buildDiff;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.List;
import org.apache.commons.io.FilenameUtils;


public class Differ {

    public static String generate(String filePath1, String filePath2) throws Exception {
        return generate(filePath1, filePath2, "stylish");
    }

    public static String generate(String filePath1, String filePath2, String format) throws Exception {
        var formate1 = getFormat(filePath1);
        var formate2 = getFormat(filePath2);

        String contentOfFilepath1 = Files.readString(Paths.get(filePath1));
        String contentOfFilepath2 = Files.readString(Paths.get(filePath2));

        Map<String, Object> config1 = Parser.parse(contentOfFilepath1, formate1);
        Map<String, Object> config2 = Parser.parse(contentOfFilepath2, formate2);
        List<DiffNode> diffNodes = buildDiff(config1, config2);
        return Formatter.format(diffNodes, format);

    }

    private static String getFormat(String sourcePath)  {

        return FilenameUtils.getExtension(sourcePath).toLowerCase();


    }

}
