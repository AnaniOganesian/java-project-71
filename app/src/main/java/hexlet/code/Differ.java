package hexlet.code;

import static hexlet.code.BuildDiff.buildDiff;
import java.io.IOException;
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

        if (formate1.equals("json") && formate2.equals("json")) {
            Map<String, Object> config1 = Parser.parseJson(contentOfFilepath1);
            Map<String, Object> config2 = Parser.parseJson(contentOfFilepath2);
            List<DiffNode> diffNodes = buildDiff(config1, config2);
            return Formatter.format(diffNodes, format);
        } else if ((formate1.equals("yaml") && formate2.equals("yaml")) || (formate1.equals("yml") && formate2.equals("yml"))) {
            Map<String, Object> config1 = Parser.parseYaml(contentOfFilepath1);
            Map<String, Object> config2 = Parser.parseYaml(contentOfFilepath2);
            List<DiffNode> diffNodes = buildDiff(config1, config2);
            return Formatter.format(diffNodes, format);
        } else {
            return "Wrong formates";
        }
    }

    private static String getFormat(String sourcePath)  {

        return FilenameUtils.getExtension(sourcePath);


    }

}
